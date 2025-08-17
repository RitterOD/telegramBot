package org.maslov.bot.app.games.random;

import org.maslov.bot.app.dao.repository.TranslationRepository;
import org.maslov.bot.app.games.model.Activity;
import org.maslov.bot.app.games.random.model.Direction;
import org.maslov.bot.app.games.random.model.RandomGameState;
import org.maslov.bot.app.games.random.model.RandomGameTranslation;
import org.maslov.bot.app.games.random.model.RandomWordGame;
import org.maslov.bot.app.games.random.model.RandomWordGameStage;
import org.maslov.bot.app.model.Translation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DefaultRandomGameService implements RandomGameService {

    public static final long DEFAULT_RANDOM_GAME_AMOUNT = 5L;
    private final TranslationRepository translationRepository;
    private final ModelMapper modelMapper;
    private final Random random;

    public DefaultRandomGameService(TranslationRepository translationRepository, ModelMapper modelMapper) {
        this.translationRepository = translationRepository;
        this.modelMapper = modelMapper;
        this.random = new Random();
    }

    @Override
    public RandomWordGame creatRandomGame() {
        var lst = translationRepository.findRandomN(DEFAULT_RANDOM_GAME_AMOUNT);
        var rv = new RandomWordGame(lst.stream().map(this::generateRandomStage).collect(Collectors.toList()));
        return rv;
    }

    @Override
    public RandomWordGame creatRandomGame(Long telegramUserId) {
        long cnt = translationRepository.countAllByTelegramUserId(telegramUserId);
        if (cnt < DEFAULT_RANDOM_GAME_AMOUNT) {
            return creatRandomGame();
        } else {
            var lst = translationRepository.findRandomNWithTelegramUserId(DEFAULT_RANDOM_GAME_AMOUNT, telegramUserId);
            return new RandomWordGame(lst.stream().map(this::generateRandomStage).collect(Collectors.toList()));
        }
    }

    @Override
    public String getInitialMessage(Activity activity) {
        if (activity instanceof RandomWordGame g) {
            return g.getInitialMessage() + "\n" + g.getCurrentStageMessage();
        } else {
            throw new RuntimeException("Wrong type of activity");
        }
    }

    @Override
    public Activity setState(Activity activity, RandomGameState state) {
        if (activity instanceof RandomWordGame g) {
            g.setState(state);
            return g;
        } else {
            throw new RuntimeException("Wrong type of activity");
        }
    }

    @Override
    public RandomGameState getInitialActivityState() {
        return RandomGameState.IN_PROGRESS;
    }

    @Override
    public String handleCurrentStateMessage(Activity activity, String message) {
        if (activity instanceof RandomWordGame g) {
            return g.handleCurrentStateMessage(message);
        } else {
            throw new RuntimeException("Wrong type of activity");
        }
    }

    @Override
    public boolean incrementStage(Activity activity) {
        if (activity instanceof RandomWordGame g) {
            return g.incrementStage();
        } else {
            throw new RuntimeException("Wrong type of activity");
        }
    }

    @Override
    public String getFinishMessage(Activity activity) {
        if (activity instanceof RandomWordGame g) {
            return g.getFinishMessage();
        } else {
            throw new RuntimeException("Wrong type of activity");
        }
    }

    @Override
    public String getCurrentStageMessage(Activity activity) {
        if (activity instanceof RandomWordGame g) {
            return g.getCurrentStageMessage();
        } else {
            throw new RuntimeException("Wrong type of activity");
        }
    }

    private RandomWordGameStage generateRandomStage(Translation translation) {
        var gameTranslation = modelMapper.map(translation, RandomGameTranslation.class);
        var stage = new RandomWordGameStage();
        stage.setTranslation(gameTranslation);
        if (random.nextDouble() < 0.5) {
            stage.setDirection(Direction.FROM);
        } else {
            stage.setDirection(Direction.TO);
        }
        return stage;
    }
}
