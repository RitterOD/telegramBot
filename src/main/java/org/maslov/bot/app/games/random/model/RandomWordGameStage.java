package org.maslov.bot.app.games.random.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.maslov.bot.app.games.model.GameStage;
import org.maslov.bot.app.games.model.GameStageResult;

import java.io.Serializable;

public class RandomWordGameStage implements GameStage, Serializable {

    @JsonProperty
    private RandomGameTranslation randomGameTranslation;

    @JsonProperty
    private Direction direction;

    @JsonProperty
    private GameStageResult gameStageResult;

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

    @JsonIgnore
    public String getInitialStageMessage() {
        var word = direction == Direction.FROM ? randomGameTranslation.getWord() :
                randomGameTranslation.getTranslation();

        return "Переведите слово: " + word;
    }

    @JsonIgnore
    public GameStageResult handleAnswer(String answer) {
        var rightAnswer = (direction == Direction.FROM ? randomGameTranslation.getTranslation()
                : randomGameTranslation.getWord());
        var isRight = rightAnswer.equalsIgnoreCase(answer);
        this.gameStageResult = new RandomGameStageResult(isRight ? 1 : 0);
        return this.gameStageResult;
    }

    @JsonIgnore
    public String getAnswerMessage(GameStageResult gameStageResult) {
        if (gameStageResult.getPoints().intValue() > 0) {
            return "правильный ответ";
        } else {
            var rightAnswer = (direction == Direction.FROM ? randomGameTranslation.getTranslation()
                    : randomGameTranslation.getWord());
            return "ошибка. правильный ответ: " + rightAnswer ;
        }
    }

    public GameStageResult getGameStageResult() {
        return gameStageResult;
    }
}
