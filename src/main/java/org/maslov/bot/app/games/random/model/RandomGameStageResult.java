package org.maslov.bot.app.games.random.model;

import org.maslov.bot.app.games.model.GameStageResult;

public class RandomGameStageResult implements GameStageResult {

    private int points;


    public RandomGameStageResult(int points) {
        this.points = points;
    }

    @Override
    public Number getPoints() {
        return points;
    }
}
