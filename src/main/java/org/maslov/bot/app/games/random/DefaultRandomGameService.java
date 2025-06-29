package org.maslov.bot.app.games.random;

import org.maslov.bot.app.dao.repository.TranslationRepository;
import org.maslov.bot.app.games.random.model.Direction;
import org.maslov.bot.app.games.random.model.RandomGameTranslation;
import org.maslov.bot.app.games.random.model.RandomWordGame;
import org.maslov.bot.app.games.random.model.RandomWordGameStage;
import org.maslov.bot.app.model.Translation;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.stream.Collectors;

@Service
public class DefaultRandomGameService implements RandomGameService{

    public static final long DEFAULT_RANDOM_GAME_AMOUNT = 10L;
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
