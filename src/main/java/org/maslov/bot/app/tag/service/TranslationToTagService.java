package org.maslov.bot.app.tag.service;

import org.maslov.bot.app.tag.dto.TagResponseDto;
import org.maslov.bot.app.tag.dto.request.TranslationToTagLinkRequest;
import org.maslov.bot.app.tag.dto.response.TranslationResponseDto;
import org.maslov.bot.app.tag.dto.response.TranslationToTagLinkResponse;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

public interface TranslationToTagService {

    TranslationToTagLinkResponse createLink(TranslationToTagLinkRequest
                                                     translationToTagLinkRequest);

    TranslationToTagLinkResponse deleteLink(TranslationToTagLinkRequest
                                                    translationToTagLinkRequest);

    List<TranslationResponseDto> getTagsTranslation( UUID tagId);
    List<TagResponseDto> getTranslationTags(UUID translationId);
}
