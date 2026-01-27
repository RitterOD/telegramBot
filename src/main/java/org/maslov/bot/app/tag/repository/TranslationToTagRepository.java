package org.maslov.bot.app.tag.repository;

import org.maslov.bot.app.model.tag.TranslationToTagLink;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TranslationToTagRepository extends JpaRepository<TranslationToTagLink, UUID> {

    List<TranslationToTagLink> findAllByTag_Id(UUID id);

    List<TranslationToTagLink> findAllByTranslation_Id(UUID id);
}
