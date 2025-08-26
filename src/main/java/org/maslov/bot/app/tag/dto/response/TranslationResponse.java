package org.maslov.bot.app.tag.dto.response;

import lombok.Value;
import org.maslov.bot.app.model.LangCode;

import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link org.maslov.bot.app.model.Translation}
 */
@Value
public class TranslationResponse {
    UUID id;
    LangCode from;
    LangCode to;
    String word;
    String wordTranscription;
    String translation;
    Set<TranslationToTagLinkDto> translationToTagLinks;
    Long telegramUserId;
    UUID technicalUserId;
    String toTranslateClue;
    String fromTranslateClue;
    String translationTranscription;
}