package org.maslov.bot.app.tag.dto.response;

import lombok.Value;

import java.util.UUID;

/**
 * DTO for {@link org.maslov.bot.app.model.tag.TranslationToTagLink}
 */
@Value
public class TranslationToTagLinkDto {
    UUID id;
    TagDto tag;
}