package org.maslov.bot.app.games;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.maslov.bot.app.games.model.Activity;
import org.maslov.bot.app.games.random.RandomGameService;
import org.maslov.bot.app.games.random.model.RandomWordGame;
import org.maslov.bot.app.model.user.TelegramUser;
import org.maslov.bot.app.model.user.UserState;
import org.maslov.bot.app.model.user.UserStatus;
import org.maslov.bot.app.service.user.TelegramUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class DefaultBotEngine implements BotEngine {

    private static final String HELLO_MSG = "Чтобы начать игру в слова команда /start. \nЧтобы закончить команда /stop";
    private static final String START_CMD = "/start";
    private static final String STOP_CMD = "/stop";
    private final Map<Long, RandomWordGame> gameMap = new ConcurrentHashMap<>();

    private final RandomGameService randomGameService;
    private final TelegramUserService telegramUserService;

    private final ObjectMapper objectMapper;

    public DefaultBotEngine(RandomGameService randomGameService, TelegramUserService telegramUserService, ObjectMapper objectMapper) {
        this.randomGameService = randomGameService;
        this.telegramUserService = telegramUserService;
        this.objectMapper = objectMapper;
    }

@Transactional
    /* TODO Fix command handling errors*/
    public List<SendMessage> consume(final Update update) {
        final var userId = update.getMessage().getFrom().getId();
        var tlgUser = fetchOrCreateTelegramUser(update);
        log.info("Message from tlg user uuid: {} tlgId: {}", tlgUser.getId(), tlgUser.getTelegramId());
//        final var chatId = update.getMessage().getChatId();
//        if (gameMap.containsKey(userId)) {
//            var game = gameMap.get(userId);
//            String resultText = game.handleCurrentStateMessage(update.getMessage().getText());
//            var finish = game.incrementStage();
//            String nextText;
//            if (finish) {
//                nextText = game.getFinishMessage();
//                gameMap.remove(userId);
//            } else {
//                nextText = game.getCurrentStageMessage();
//            }
//
//            return List.of(buildMessage(chatId, resultText), buildMessage(chatId, nextText));
//        } else if (update.getMessage().hasText()) {
//            var text = update.getMessage().getText();
//            if (START_CMD.equals(text)) {
//                RandomWordGame game = randomGameService.creatRandomGame();
//                gameMap.put(userId, game);
//                var initialMsg = game.getInitialMessage();
//                game.setState(RandomGameState.IN_PROGRESS);
//                initialMsg = initialMsg + "\n" + game.getCurrentStageMessage();
//                SendMessage message = SendMessage // Create a message object
//                        .builder()
//                        .chatId(update.getMessage().getChatId())
//                        .text(initialMsg)
//                        .build();
//                return List.of(message);
//
//            } else if (STOP_CMD.equals(text)) {
//                gameMap.remove(userId);
//                return List.of(SendMessage // Create a message object
//                        .builder()
//                        .chatId(update.getMessage().getChatId())
//                        .text("Игра прекращена")
//                        .build());
//            } else {
//                SendMessage message = SendMessage // Create a message object
//                        .builder()
//                        .chatId(update.getMessage().getChatId())
//                        .text(HELLO_MSG)
//                        .build();
//                return List.of(message);
//            }
//        }
//        return List.of(SendMessage // Create a message object
//                .builder()
//                .chatId(update.getMessage().getChatId())
//                .text("")
//                .build());
        return process(tlgUser, update);
    }

    private TelegramUser fetchOrCreateTelegramUser(final Update update) {
        final var userId = update.getMessage().getFrom().getId();
        return telegramUserService.findUserByTelegramId(userId).orElseGet(() -> {
            var user = update.getMessage().getFrom();
            return telegramUserService.save(userId, user.getFirstName(), user.getUserName(), user.getLanguageCode());
        });
    }

    /*
     * FSM for processing messages
     * */
    private List<SendMessage> process(TelegramUser user, Update update) {
        final var chatId = update.getMessage().getChatId();
        List<SendMessage> rv = List.of(buildMessage(chatId, ""));
        if (update.getMessage().hasText()) {
            var text = update.getMessage().getText();
            if (START_CMD.equals(text)) {
                if (user.getState().getActivity() == null) {
                    // TODO REFACTOR remove dependency from activity implementation
                    Activity game = randomGameService.creatRandomGame();
//                    gameMap.put(userId, game);
                    var initialMsg = randomGameService.getInitialMessage(game);
                    game = randomGameService.setState(game, randomGameService.getInitialActivityState());
                    user.getState().setActivity(game);
                    user.getState().setStatus(UserStatus.IN_ACTIVITY);
                    SendMessage message = buildMessage(chatId, initialMsg);
                    rv = List.of(message);

                } else {
                    //
                }
            } else if (STOP_CMD.equals(text)) {
                user.getState().setActivity(null);
                user.getState().setStatus(UserStatus.IDLE);
                rv = buildListMessage(chatId,"Игра прекращена");
            } else {
                if (user.getState().getActivity() != null) {
                    var activity = user.getState().getActivity();
                    String resultText = randomGameService.handleCurrentStateMessage(activity, update.getMessage().getText());
                    var finish = randomGameService.incrementStage(activity);
                    String nextText;
                    if (finish) {
                        nextText = randomGameService.getFinishMessage(activity);
                        user.getState().setActivity(null);
                        user.getState().setStatus(UserStatus.IDLE);
                    } else {
                        nextText = randomGameService.getCurrentStageMessage(activity);
                    }
                    rv = List.of(buildMessage(chatId, resultText), buildMessage(chatId, nextText));
                }
            }
        } else {
        }
        telegramUserService.save(user);
        return rv;
    }

    private SendMessage buildMessage(Long chatId, String text) {
        return SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(text)
                .build();
    }

    private List<SendMessage> buildListMessage(Long chatId, String text) {
        return List.of(SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(text)
                .build());
    }
}
