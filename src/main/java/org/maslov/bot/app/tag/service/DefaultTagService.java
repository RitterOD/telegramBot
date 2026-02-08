package org.maslov.bot.app.tag.service;

import org.maslov.bot.app.model.tag.Tag;
import org.maslov.bot.app.tag.dto.TagDto;
import org.maslov.bot.app.tag.dto.TagResponseDto;
import org.maslov.bot.app.tag.dto.request.TagLinkRequest;
import org.maslov.bot.app.tag.dto.response.TagLinkResponse;
import org.maslov.bot.app.tag.dto.response.TranslationResponse;
import org.maslov.bot.app.tag.repository.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;

import java.util.List;
import java.util.Optional;
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
    public Page<TagResponseDto> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable).map(entity -> modelMapper.map(entity, TagResponseDto.class));
    }

    @Override
    public TagResponseDto findById(UUID id) {
        return modelMapper.map(tagRepository.findById(id), TagResponseDto.class);
    }

    @Override
    @Transactional
    public Optional<TagResponseDto> delete(UUID id) {
        var tag = tagRepository.findById(id).orElse(null);;
        if (tag != null) {
            tagRepository.delete(tag);
            return Optional.of(modelMapper.map(tag, TagResponseDto.class));
        }
        return Optional.empty();
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
