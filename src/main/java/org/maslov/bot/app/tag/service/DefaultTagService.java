package org.maslov.bot.app.tag.service;

import org.maslov.bot.app.model.tag.Tag;
import org.maslov.bot.app.tag.dto.TagDto;
import org.maslov.bot.app.tag.dto.TagResponseDto;
import org.maslov.bot.app.tag.dto.request.TagLinkRequest;
import org.maslov.bot.app.tag.dto.response.TagLinkResponse;
import org.maslov.bot.app.tag.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultTagService implements TagService {


    private final TagRepository tagRepository;
    private final ModelMapper modelMapper;


    public DefaultTagService(TagRepository tagRepository, ModelMapper modelMapper) {
        this.tagRepository = tagRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public TagResponseDto createTag(TagDto tagDto) {
        var entity = modelMapper.map(tagDto, Tag.class);
        entity = tagRepository.save(modelMapper.map(tagDto, Tag.class));
        return modelMapper.map(entity, TagResponseDto.class);
    }

    @Override
    public Slice<TagResponseDto> findAll(Pageable pageable) {
        var entitySlice = tagRepository.findAll(pageable);
        var content = entitySlice.stream().map(e -> modelMapper.map(e, TagResponseDto.class)).toList();
        return new SliceImpl<>(content, pageable, entitySlice.hasNext());
    }

    @Override
    public TagResponseDto findById(UUID id) {
        return modelMapper.map(tagRepository.findById(id), TagResponseDto.class);
    }

    @Override
    @Transactional
    public TagLinkResponse createLink(TagLinkRequest tagLinkRequest) {
        var parentTag = tagRepository.findById(tagLinkRequest.getParentId()).orElseThrow(() -> new RuntimeException("Parent tag not found"));
        var childTag = tagRepository.findById(tagLinkRequest.getChildId()).orElseThrow(() -> new RuntimeException("Child tag not found"));
        var parentLinks = parentTag.getToChildrenLinks();
        parentLinks.forEach(e -> {
            if (e.getChildrenTag().getId().equals(tagLinkRequest.getChildId())) {
                throw new RuntimeException("Link already exists");
            }
        });
        parentTag.addTagLink(childTag);
        tagRepository.save(parentTag);
        tagRepository.save(childTag);
        return modelMapper.map(tagLinkRequest, TagLinkResponse.class);
    }
}
