package org.maslov.bot.app.games.random.model;

import org.maslov.bot.app.games.model.DefaultGameStageResult;
import org.maslov.bot.app.games.model.Game;
import org.maslov.bot.app.games.model.GameStageResult;

import java.util.List;
import java.util.Objects;

public class RandomWordGame implements Game {

    private RandomGameState state;
    private int stageNum;
    private final List<RandomWordGameStage> randomWordGameStages;

    private final static String INITIAL_MESSAGE = "Игра в перевод слов";

    public void setState(RandomGameState state) {
        this.state = state;
    }

    public String getInitialMessage() {
        return INITIAL_MESSAGE;
    }

    public RandomWordGame(List<RandomWordGameStage> randomWordGameStages) {
        this.randomWordGameStages = randomWordGameStages;
        this.state = RandomGameState.INIT;
        this.stageNum = 0;
    }

    public String getCurrentStageMessage() {
        return randomWordGameStages.get(stageNum).getInitialStageMessage();
    }

    public String handleCurrentStateMessage(String message) {
        var currentState = randomWordGameStages.get(stageNum);
        var result = currentState.handleAnswer(message);
        return currentState.getAnswerMessage(result);
    }

    public boolean incrementStage() {
        ++stageNum;
        if (stageNum >= randomWordGameStages.size()) {
            return true;
        } else {
            return false;
        }
    }

    public String getFinishMessage() {
        var right = randomWordGameStages.stream().map(RandomWordGameStage::getGameStageResult)
                .filter(Objects::nonNull).map(GameStageResult::getPoints).map(Number::intValue).reduce(0, Integer::sum);
        StringBuilder sb = new StringBuilder("Игра окончена. Всего слов: ").append(randomWordGameStages.size());
        sb.append("\n Правильно: ").append(right);
        sb.append("\n Неравильно: ").append(randomWordGameStages.size() - right);
        return sb.toString();
    }
}
