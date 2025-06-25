package org.maslov.bot.app.games.random.model;

import org.maslov.bot.app.games.model.GameStage;

public class RandomWordGameStage implements GameStage {
    private Translation translation;

    public Translation getTranslation() {
        return translation;
    }

    public void setTranslation(Translation translation) {
        this.translation = translation;
    }
}
