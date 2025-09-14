package org.maslov.bot.app.games.random;

import org.maslov.bot.app.dao.repository.TranslationRepository;
import org.maslov.bot.app.model.Translation;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class DefaultRandomWordsServiceProvider implements RandomWordsProviderService{

    private final TranslationCache translationCache;
    private final TranslationRepository translationRepository;
    private final Random random;

    public DefaultRandomWordsServiceProvider(TranslationCache translationCache, TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
        this.random = new Random(System.currentTimeMillis());
        this.translationCache = translationCache;
    }

    @Override
    public List<Translation> findRandomNWithTelegramUserId(Long amount, Long telegramUserId) {
        var rv = translationCache.getTlgUserCache(telegramUserId);
        if (rv.size() < amount) {
            rv = translationCache.getAllTranslationCache();
        }
        List<UUID> ids = new ArrayList<>();
        for(var i = 0; i < amount; ++i) {
            ids.add(rv.get(random.nextInt(rv.size())));
        }
        return translationRepository.findAllById(ids);
    }
}
