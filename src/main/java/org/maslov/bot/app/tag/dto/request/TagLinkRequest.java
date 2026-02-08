package org.maslov.bot.app.tag.dto.request;

import lombok.Builder;

import java.util.UUID;

@Builder
public class TagLinkRequest {
    private UUID childId;
    private UUID parentId;

    public UUID getChildId() {
        return childId;
    }

    public void setChildId(UUID childId) {
        this.childId = childId;
    }

    public UUID getParentId() {
        return parentId;
    }

    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }
}
