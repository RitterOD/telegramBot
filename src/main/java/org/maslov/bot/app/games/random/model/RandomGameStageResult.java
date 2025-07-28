package org.maslov.bot.app.games.random.model;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.maslov.bot.app.games.model.GameStageResult;

public class RandomGameStageResult implements GameStageResult {

    @JsonProperty
    private int points;

    public RandomGameStageResult() {
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public RandomGameStageResult(int points) {
        this.points = points;
    }

    @Override
    public Number getPoints() {
        return points;
    }
}
