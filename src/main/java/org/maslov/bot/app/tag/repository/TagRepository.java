package org.maslov.bot.app.tag.repository;

import org.maslov.bot.app.model.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface TagRepository extends JpaRepository<Tag, UUID> {
}