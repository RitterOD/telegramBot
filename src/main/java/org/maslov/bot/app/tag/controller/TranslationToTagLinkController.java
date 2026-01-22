package org.maslov.bot.app.tag.controller;

import org.maslov.bot.app.tag.dto.request.TranslationToTagLinkRequest;
import org.maslov.bot.app.tag.dto.response.TranslationResponseDto;
import org.maslov.bot.app.tag.dto.response.TranslationToTagLinkResponse;
import org.maslov.bot.app.tag.service.TranslationToTagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/translationToTagLink")
public class TranslationToTagLinkController
{

    private final TranslationToTagService translationToTagService;

    public TranslationToTagLinkController(TranslationToTagService translationToTagService) {
        this.translationToTagService = translationToTagService;
    }

    @PostMapping
    public ResponseEntity<TranslationToTagLinkResponse> createLink(@RequestBody TranslationToTagLinkRequest
                                                                           translationToTagLinkRequest){
        return ResponseEntity.ok(null);
    }

    @DeleteMapping
    public ResponseEntity<TranslationToTagLinkResponse> deleteLink(@RequestBody TranslationToTagLinkRequest
                                                                           translationToTagLinkRequest) {
        return ResponseEntity.ok(null);
    }

    @GetMapping("/tagstranslations/{tagId}")
    public ResponseEntity<List<TranslationResponseDto>> getTagsTranslation(@PathVariable("tagId") UUID tagId){
        return ResponseEntity.ok(translationToTagService.getTagsTranslation(tagId));
    }

    @GetMapping("/tagstranslations/{translationId}")
    public ResponseEntity<List<TranslationResponseDto>> getTranslationTags(@PathVariable("translationId") UUID tagId){
        return ResponseEntity.ok(null);
    }


}
