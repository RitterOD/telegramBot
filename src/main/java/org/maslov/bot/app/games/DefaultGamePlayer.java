package org.maslov.bot.app.games;

import org.maslov.bot.app.games.random.RandomGameService;
import org.maslov.bot.app.games.random.model.RandomGameState;
import org.maslov.bot.app.games.random.model.RandomWordGame;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class DefaultGamePlayer implements GamePlayer {

    private static final String HELLO_MSG = "Чтобы начать игру в слова команда /start. \nЧтобы закончить команда /stop";
    private static final String START_CMD = "/start";
    private static final String STOP_CMD = "/stop";
    private final Map<Long, RandomWordGame> gameMap = new ConcurrentHashMap<>();

    private final RandomGameService randomGameService;

    public DefaultGamePlayer(RandomGameService randomGameService) {
        this.randomGameService = randomGameService;
    }


    /* TODO Fix command handling errors*/
    public List<SendMessage> consume(Update update) {
        final var userId = update.getMessage().getFrom().getId();
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

    private SendMessage buildMessage(Long chatId, String text) {
        return SendMessage // Create a message object
                .builder()
                .chatId(chatId)
                .text(text)
                .build();
    }
}
