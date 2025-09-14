package org.maslov.bot.app.games.random;

import org.maslov.bot.app.dao.repository.TranslationRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class TranslationCache {

    private final TranslationRepository translationRepository;

    public TranslationCache(TranslationRepository translationRepository) {
        this.translationRepository = translationRepository;
    }

    @Cacheable(cacheNames = "telegramUserIdTranslationCache")
    public Map<Integer, UUID> getTlgUserCache(Long telegramUserId) {
        List<UUID> ids = translationRepository.findAllTelegramUserTranslationIds(telegramUserId);
        var numberToUuidCache = new HashMap<Integer, UUID>();
        for (int i = 0; i < ids.size(); ++i) {
            numberToUuidCache.put(i, ids.get(i));
        }
        return numberToUuidCache;

    }

    @Cacheable(cacheNames = "allTranslationIdsCache")
    public Map<Integer, UUID> getAllTranslationCache() {
        var numberToUuidCache = new HashMap<Integer, UUID>();
        List<UUID> ids = translationRepository.findAllIds();
        for (int i = 0; i < ids.size(); ++i) {
            numberToUuidCache.put(i, ids.get(i));
        }
        return numberToUuidCache;
    }
}
