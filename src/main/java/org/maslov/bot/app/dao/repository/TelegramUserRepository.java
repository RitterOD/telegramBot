package org.maslov.bot.app.dao.repository;

import org.maslov.bot.app.model.tlg.user.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, UUID> {

    Optional<TelegramUser> findByTelegramId(Long telegramId);
}
