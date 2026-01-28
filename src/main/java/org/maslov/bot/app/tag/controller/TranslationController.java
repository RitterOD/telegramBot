package org.maslov.bot.app.tag.controller;

import com.fasterxml.jackson.databind.JsonNode;
import io.javalin.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import org.maslov.bot.app.tag.dto.request.TranslationRequest;
import org.maslov.bot.app.tag.dto.response.TranslationResponse;
import org.maslov.bot.app.tag.service.TranslationService;
import org.maslov.bot.app.model.Translation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.maslov.bot.app.tag.controller.TranslationController.ROOT_URL;

@RestController
@RequestMapping(ROOT_URL)
@RequiredArgsConstructor
public class TranslationController {

    public static final String ROOT_URL = "/api/translations";

    private final TranslationService translationService;

    @GetMapping
    public PagedModel<TranslationResponse> getAll(Pageable pageable) {
        Page<TranslationResponse> translations = translationService.getAll(pageable);
        return new PagedModel<>(translations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TranslationResponse> getOne(@PathVariable UUID id) {
        return ResponseEntity.ok(translationService.getOne(id));
    }

    @GetMapping("/by-ids")
    public List<TranslationResponse> getMany(@RequestParam List<UUID> ids) {
        return translationService.getMany(ids);
    }

    @PostMapping
    public ResponseEntity<TranslationResponse> create(@RequestBody TranslationRequest translation) {
        return ResponseEntity.ok(translationService.create(translation));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TranslationResponse> patch(@PathVariable UUID id, @RequestBody JsonNode patchNode) throws IOException {
        return ResponseEntity.ok(translationService.patch(id, patchNode));
    }

    @PatchMapping
    public ResponseEntity<List<UUID>> patchMany(@RequestParam List<UUID> ids, @RequestBody JsonNode patchNode) throws IOException {
        return ResponseEntity.ok(translationService.patchMany(ids, patchNode));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TranslationResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(translationService.delete(id));
    }

    @DeleteMapping
    public void deleteMany(@RequestParam List<UUID> ids) {
        translationService.deleteMany(ids);
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        // A generic 500 error message is returned to the client
        return ResponseEntity.internalServerError().body ("An internal server error occurred");
    }
}
