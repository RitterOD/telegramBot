package org.maslov.bot.app.games;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.maslov.bot.app.games.model.Activity;
import org.maslov.bot.app.games.random.RandomGameService;
import org.maslov.bot.app.games.random.model.RandomWordGame;
import org.maslov.bot.app.model.tlg.user.TelegramUser;
import org.maslov.bot.app.model.tlg.user.UserStatus;
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

    /* TODO Fix command handling errors*/
    @Transactional
    public List<SendMessage> consume(final Update update) {
        final var userId = update.getMessage().getFrom().getId();
        var tlgUser = fetchOrCreateTelegramUser(update);
        log.info("Message from tlg user uuid: {} tlgId: {}", tlgUser.getId(), tlgUser.getTelegramId());
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
        final var userId = update.getMessage().getFrom().getId();
        List<SendMessage> rv = List.of(buildMessage(chatId, ""));
        if (update.getMessage().hasText()) {
            var text = update.getMessage().getText();
            if (START_CMD.equals(text)) {
                if (user.getState().getActivity() == null) {
                    // TODO REFACTOR remove dependency from activity implementation
                    Activity game = randomGameService.creatRandomGame(userId);
                    var initialMsg = randomGameService.getInitialMessage(game);
                    game = randomGameService.setState(game, randomGameService.getInitialActivityState());
                    user.getState().setActivity(game);
                    user.getState().setStatus(UserStatus.IN_ACTIVITY);
                    SendMessage message = buildMessage(chatId, initialMsg);
                    rv = List.of(message);

                } else {
                   rv = buildListMessage(chatId, getInGameMessageForStartCmd());
                }
            } else if (STOP_CMD.equals(text)) {
                if (user.getState().getStatus() == UserStatus.IDLE) {
                    rv = buildListMessage(chatId, getNotInGameMessageForStopCmd());
                } else {
                    user.getState().setActivity(null);
                    user.getState().setStatus(UserStatus.IDLE);
                    rv = buildListMessage(chatId, "Игра прекращена");
                }
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

    private String getNotInGameMessageForStopCmd() {
        return """
                Сейчас вы не в игре, чтобы начать игру наберите  /start.
                Помощь /help
                """;
    }

    private String getInGameMessageForStartCmd() {
        return """
                Сейчас вы не в игре, чтобы завершить игру наберите  /stop.
                Помощь /help
                """;
    }
}
