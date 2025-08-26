package org.maslov.bot.app.tag.dto.response;

import lombok.Value;

import java.util.UUID;

/**
 * DTO for {@link org.maslov.bot.app.model.tag.Tag}
 */
@Value
public class TagDto {
    UUID id;
}