package org.maslov.bot.app.service.user;

import org.maslov.bot.app.model.TelegramUser;

import java.util.Optional;

public interface TelegramUserService {
    Optional<TelegramUser> findUserByTelegramId(Long id);

    TelegramUser save(TelegramUser telegramUser);

    TelegramUser save(Long id, String firstName, String userName, String langCode);
}
