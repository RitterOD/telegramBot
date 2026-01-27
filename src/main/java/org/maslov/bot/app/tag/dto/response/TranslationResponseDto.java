package org.maslov.bot.app.tag.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Value;
import org.maslov.bot.app.model.LangCode;

import java.util.UUID;

/**
 * DTO for {@link org.maslov.bot.app.model.Translation}
 */
@Value
@JsonIgnoreProperties(ignoreUnknown = true)
public class TranslationResponseDto {
    UUID id;
    LangCode from;
    LangCode to;
    String word;
    String wordTranscription;
    String translation;
    String toTranslateClue;
    String fromTranslateClue;
    String translationTranscription;
}