package org.maslov.bot.app.tag.controller;

import org.maslov.bot.app.tag.dto.request.TranslationToTagLinkRequest;
import org.maslov.bot.app.tag.dto.response.TranslationResponseDto;
import org.maslov.bot.app.tag.dto.response.TranslationToTagLinkResponse;
import org.maslov.bot.app.tag.service.TranslationToTagService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static org.maslov.bot.app.tag.controller.TranslationToTagLinkController.ROOT_URL;

@RestController
@RequestMapping(ROOT_URL)
public class TranslationToTagLinkController
{

    public static final String ROOT_URL = "/api/translationToTagLink";
    private final TranslationToTagService translationToTagService;

    public TranslationToTagLinkController(TranslationToTagService translationToTagService) {
        this.translationToTagService = translationToTagService;
    }

    @PostMapping
    public ResponseEntity<TranslationToTagLinkResponse> createLink(@RequestBody TranslationToTagLinkRequest
                                                                           translationToTagLinkRequest){
        return ResponseEntity.ok(translationToTagService.createLink(translationToTagLinkRequest));
    }

    @DeleteMapping
    public ResponseEntity<TranslationToTagLinkResponse> deleteLink(@RequestBody TranslationToTagLinkRequest
                                                                           translationToTagLinkRequest) {
        return ResponseEntity.ok(translationToTagService.deleteLink(translationToTagLinkRequest));
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
