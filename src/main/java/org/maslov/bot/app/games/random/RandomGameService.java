package org.maslov.bot.app.games.random;

import org.maslov.bot.app.games.model.Activity;
import org.maslov.bot.app.games.random.model.RandomGameState;
import org.maslov.bot.app.games.random.model.RandomWordGame;

public interface RandomGameService {
    RandomWordGame creatRandomGame();

    String getInitialMessage(Activity activity);

    Activity setState(Activity activity, RandomGameState state);

    RandomGameState getInitialActivityState();


    String handleCurrentStateMessage(Activity activity, String message);

    boolean incrementStage(Activity activity);

    String getFinishMessage(Activity activity);


    String getCurrentStageMessage(Activity activity);
}
