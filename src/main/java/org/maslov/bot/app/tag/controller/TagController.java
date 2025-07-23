package org.maslov.bot.app.tag.controller;

import org.maslov.bot.app.tag.dto.TagDto;
import org.maslov.bot.app.tag.dto.TagResponseDto;
import org.maslov.bot.app.tag.service.TagService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/tag")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public ResponseEntity<TagResponseDto> createTag(@RequestBody TagDto tagDto) {
        return ResponseEntity.ok(tagService.createTag(tagDto));
    }

    @GetMapping
    public ResponseEntity<Slice<TagResponseDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(tagService.findAll(pageable));
    }
}
