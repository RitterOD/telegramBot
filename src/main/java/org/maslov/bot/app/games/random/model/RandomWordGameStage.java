package org.maslov.bot.app.games.random.model;

import org.maslov.bot.app.games.model.GameStage;
import org.maslov.bot.app.games.model.GameStageResult;

public class RandomWordGameStage implements GameStage {
    private RandomGameTranslation randomGameTranslation;
    private Direction direction;

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public RandomGameTranslation getTranslation() {
        return randomGameTranslation;
    }

    public void setTranslation(RandomGameTranslation randomGameTranslation) {
        this.randomGameTranslation = randomGameTranslation;
    }

    public String getInitialStageMessage() {
        var word = direction == Direction.FROM ? randomGameTranslation.getWord() :
                randomGameTranslation.getTranslation();

        return "Переведите слово: " + word;
    }

    public GameStageResult handleAnswer(String answer) {
        var rightAnswer = (direction == Direction.FROM ? randomGameTranslation.getTranslation()
                : randomGameTranslation.getWord());
        var isRight = rightAnswer.equalsIgnoreCase(answer);
        return new RandomGameStageResult(isRight ? 1 : 0);
    }

    public String getAnswerMessage(GameStageResult gameStageResult) {
        if (gameStageResult.getPoints().intValue() > 0) {
            return "правильный ответ";
        } else {
            return "ошибка";
        }
    }
}
