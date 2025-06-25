package org.maslov.bot.app.games.random;

import org.maslov.bot.app.games.random.model.RandomWordGame;
import org.springframework.stereotype.Service;

@Service
public class DefaultRandomGameService implements RandomGameService{
    @Override
    public RandomWordGame creatRandomGame() {
        return null;
    }
}
