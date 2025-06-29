package org.maslov.bot.app.games.random.model;

import org.maslov.bot.app.games.model.GameStage;

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
}
