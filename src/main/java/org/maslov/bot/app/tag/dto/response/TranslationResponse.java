package org.maslov.bot.app.tag.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.maslov.bot.app.model.LangCode;

import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link org.maslov.bot.app.model.Translation}
 */
@NoArgsConstructor
@Getter
@Setter
public class TranslationResponse {
    private UUID id;
    private LangCode from;
    private LangCode to;
    private String word;
    private String wordTranscription;
    private String translation;
    private Set<TranslationToTagLinkDto> translationToTagLinks;
    private Long telegramUserId;
    private UUID technicalUserId;
    private String toTranslateClue;
    private String fromTranslateClue;
    private String translationTranscription;
}