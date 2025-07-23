package org.maslov.bot.app.tag.dto;

import lombok.Value;

import java.util.Date;
import java.util.UUID;

/**
 * DTO for {@link org.maslov.bot.app.model.tag.Tag}
 */
public class TagResponseDto {

    public TagResponseDto() {
    }

    Date created;
    Date updated;
    String id;
    String name;

    public String getName() {
        return name;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    String description;




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