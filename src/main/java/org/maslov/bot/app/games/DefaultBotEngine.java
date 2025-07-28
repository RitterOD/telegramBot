package org.maslov.bot.app.games;

import lombok.extern.slf4j.Slf4j;
import org.maslov.bot.app.games.random.RandomGameService;
import org.maslov.bot.app.games.random.model.RandomGameState;
import org.maslov.bot.app.games.random.model.RandomWordGame;
import org.maslov.bot.app.model.user.TelegramUser;
import org.maslov.bot.app.service.user.TelegramUserService;
import org.springframework.stereotype.Service;
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

    public DefaultBotEngine(RandomGameService randomGameService, TelegramUserService telegramUserService) {
        this.randomGameService = randomGameService;
        this.telegramUserService = telegramUserService;
    }


    /* TODO Fix command handling errors*/
    public List<SendMessage> consume(final Update update) {
        final var userId = update.getMessage().getFrom().getId();
        var tlgUser = fetchOrCreateTelegramUser(update);
        log.info("Message from tlg user uuid: {} tlgId:{}", tlgUser.getId(), tlgUser.getTelegramId());
        final var chatId = update.getMessage().getChatId();
        if (gameMap.containsKey(userId)) {
            var game = gameMap.get(userId);
            String resultText = game.handleCurrentStateMessage(update.getMessage().getText());
            var finish = game.incrementStage();
            String nextText;
            if (finish) {
                nextText = game.getFinishMessage();
                gameMap.remove(userId);
            } else {
                nextText = game.getCurrentStageMessage();
            }

            return List.of(buildMessage(chatId, resultText), buildMessage(chatId, nextText));
        } else if (update.getMessage().hasText()) {
            var text = update.getMessage().getText();
            if (START_CMD.equals(text)) {
                RandomWordGame game = randomGameService.creatRandomGame();
                gameMap.put(userId, game);
                var initialMsg = game.getInitialMessage();
                game.setState(RandomGameState.IN_PROGRESS);
                initialMsg = initialMsg + "\n" + game.getCurrentStageMessage();
                SendMessage message = SendMessage // Create a message object
                        .builder()
                        .chatId(update.getMessage().getChatId())
                        .text(initialMsg)
                        .build();
                return List.of(message);

            } else if (STOP_CMD.equals(text)) {
                gameMap.remove(userId);
                return List.of(SendMessage // Create a message object
                        .builder()
                        .chatId(update.getMessage().getChatId())
                        .text("Игра прекращена")
                        .build());
            } else {
                SendMessage message = SendMessage // Create a message object
                        .builder()
                        .chatId(update.getMessage().getChatId())
                        .text(HELLO_MSG)
                        .build();
                return List.of(message);
            }
        }
        return List.of(SendMessage // Create a message object
                .builder()
                .chatId(update.getMessage().getChatId())
                .text("")
                .build());
    }

    private TelegramUser fetchOrCreateTelegramUser(final Update update) {
        final var userId = update.getMessage().getFrom().getId();
        return telegramUserService.findUserByTelegramId(userId).orElseGet(() -> {
            var user =   update.getMessage().getFrom();
            return telegramUserService.save(userId, user.getFirstName(), user.getUserName(), user.getLanguageCode());
        });
    }

    private SendMessage buildMessage(Long chatId, String text) {
        return SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(text)
                .build();
    }
}
