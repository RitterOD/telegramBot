package org.maslov.bot.app.dao.repository;

import org.maslov.bot.app.model.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TelegramUserRepository extends JpaRepository<TelegramUser, UUID> {
}
