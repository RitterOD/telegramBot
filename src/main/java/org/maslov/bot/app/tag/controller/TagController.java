package org.maslov.bot.app.tag.controller;

import org.maslov.bot.app.tag.dto.TagDto;
import org.maslov.bot.app.tag.dto.TagResponseDto;
import org.maslov.bot.app.tag.dto.request.TagLinkRequest;
import org.maslov.bot.app.tag.dto.response.TagLinkResponse;
import org.maslov.bot.app.tag.service.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.maslov.bot.app.tag.controller.TagController.ROOT_URL;

@RestController
@RequestMapping(ROOT_URL)
public class TagController {

    public static final String ROOT_URL = "/api/tag";

    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<TagResponseDto> createTag(@RequestBody TagDto tagDto) {
        return ResponseEntity.ok(tagService.createTag(tagDto));
    }

    @GetMapping()
    public ResponseEntity<Page<TagResponseDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(tagService.findAll(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagResponseDto> get(@PathVariable("id")UUID id) {
        return ResponseEntity.ok(tagService.findById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TagResponseDto> delete(@PathVariable UUID id) {
        var opt = tagService.delete(id);
        return opt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/link")
    public ResponseEntity<TagLinkResponse> createTagToTagLink(@RequestBody TagLinkRequest tagLinkRequest) {
        return ResponseEntity.ok(tagService.createLink(tagLinkRequest));
    }
}
