package org.maslov.bot.app.tag.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.RequiredArgsConstructor;
import org.maslov.bot.app.tag.dto.request.TranslationRequest;
import org.maslov.bot.app.tag.dto.response.TranslationResponse;
import org.maslov.bot.app.tag.service.TranslationService;
import org.maslov.bot.app.model.Translation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/translations")
@RequiredArgsConstructor
public class TranslationController {

    private final TranslationService translationService;

    @GetMapping
    public PagedModel<Translation> getAll(Pageable pageable) {
        Page<Translation> translations = translationService.getAll(pageable);
        return new PagedModel<>(translations);
    }

    @GetMapping("/{id}")
    public TranslationResponse getOne(@PathVariable UUID id) {
        return translationService.getOne(id);
    }

    @GetMapping("/by-ids")
    public List<TranslationResponse> getMany(@RequestParam List<UUID> ids) {
        return translationService.getMany(ids);
    }

    @PostMapping
    public TranslationResponse create(@RequestBody TranslationRequest translation) {
        return translationService.create(translation);
    }

    @PatchMapping("/{id}")
    public TranslationResponse patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return translationService.patch(id, patchNode);
    }

    @PatchMapping
    public List<UUID> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return translationService.patchMany(ids, patchNode);
    }

    @DeleteMapping("/{id}")
    public TranslationResponse delete(@PathVariable UUID id) {
        return translationService.delete(id);
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<UUID> ids) {
        translationService.deleteMany(ids);
    }
}
