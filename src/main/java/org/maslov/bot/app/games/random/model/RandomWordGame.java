package org.maslov.bot.app.games.random.model;

import org.maslov.bot.app.games.model.DefaultGameStageResult;
import org.maslov.bot.app.games.model.Game;

import java.util.List;

public class RandomWordGame implements Game {
    private List<RandomWordGameStage> randomWordGameStages;
    private List<DefaultGameStageResult> gameStageResults;
}
