package org.maslov.bot.app.dao.repository;

import org.maslov.bot.app.model.Translation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TranslationRepository extends JpaRepository<Translation, UUID> {
}
