package org.maslov.bot.app.tag.service;

import org.maslov.bot.app.tag.dto.TagDto;
import org.maslov.bot.app.tag.dto.TagResponseDto;
import org.maslov.bot.app.tag.dto.request.TagLinkRequest;
import org.maslov.bot.app.tag.dto.response.TagLinkResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.Optional;
import java.util.UUID;

public interface TagService {

    TagResponseDto createTag(TagDto tagDto);

    Page<TagResponseDto> findAll(Pageable pageable);

    TagResponseDto findById(UUID id);

    Optional<TagResponseDto> delete(UUID id);

    TagLinkResponse createLink(TagLinkRequest tagLinkRequest);
}
