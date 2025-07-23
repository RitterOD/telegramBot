package org.maslov.bot.app.tag.dto;

import lombok.Value;

import java.util.UUID;

/**
 * DTO for {@link org.maslov.bot.app.model.tag.Tag}
 */
public class TagDto {
    String name;
    String description;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}