package org.maslov.bot.app.tag.service;

import org.maslov.bot.app.model.tag.Tag;
import org.maslov.bot.app.tag.dto.TagDto;
import org.maslov.bot.app.tag.dto.TagResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.UUID;

public interface TagService {

    TagResponseDto createTag(TagDto tagDto);

    Slice<TagResponseDto> findAll(Pageable pageable);

    TagResponseDto findById(UUID id);


}
