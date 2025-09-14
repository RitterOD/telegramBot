package org.maslov.bot.app.games.random;

import org.maslov.bot.app.model.Translation;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RandomWordsProviderService {
    List<Translation> findRandomNWithTelegramUserId(Long amount, Long telegramUserId);
}
