package org.maslov.bot.app.tag.dto.request;

import lombok.Value;
import org.maslov.bot.app.model.LangCode;

import java.util.UUID;

/**
 * DTO for {@link org.maslov.bot.app.model.Translation}
 */
@Value
public class TranslationRequest {
    LangCode from;
    LangCode to;
    String word;
    String wordTranscription;
    String translation;
    UUID technicalUserId;
    String toTranslateClue;
    String fromTranslateClue;
    String translationTranscription;
}