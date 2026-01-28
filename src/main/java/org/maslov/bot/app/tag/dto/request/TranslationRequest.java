package org.maslov.bot.app.tag.dto.request;

import lombok.*;
import org.maslov.bot.app.model.LangCode;

import java.util.UUID;

/**
 * DTO for {@link org.maslov.bot.app.model.Translation}
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class TranslationRequest {
    private LangCode from;
    private LangCode to;
    private String word;
    private String wordTranscription;
    private String translation;
    private UUID technicalUserId;
    private String toTranslateClue;
    private String fromTranslateClue;
    private String translationTranscription;
}