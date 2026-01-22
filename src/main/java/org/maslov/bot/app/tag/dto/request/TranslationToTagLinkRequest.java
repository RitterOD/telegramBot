package org.maslov.bot.app.tag.dto.request;

import java.util.UUID;

public class TranslationToTagLinkRequest {
    private UUID tagId;
    private UUID translationId;

    public UUID getTagId() {
        return tagId;
    }

    public void setTagId(UUID tagId) {
        this.tagId = tagId;
    }

    public UUID getTranslationId() {
        return translationId;
    }

    public void setTranslationId(UUID translationId) {
        this.translationId = translationId;
    }
}
