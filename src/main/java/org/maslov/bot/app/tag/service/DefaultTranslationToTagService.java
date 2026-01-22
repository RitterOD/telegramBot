package org.maslov.bot.app.tag.service;

import org.maslov.bot.app.dao.repository.TranslationRepository;
import org.maslov.bot.app.model.tag.TranslationToTagLink;
import org.maslov.bot.app.tag.dto.TagResponseDto;
import org.maslov.bot.app.tag.dto.request.TranslationToTagLinkRequest;
import org.maslov.bot.app.tag.dto.response.TranslationResponseDto;
import org.maslov.bot.app.tag.dto.response.TranslationToTagLinkResponse;
import org.maslov.bot.app.tag.repository.TagRepository;
import org.maslov.bot.app.tag.repository.TranslationToTagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.BeanFactoryTransactionAttributeSourceAdvisor;

import java.util.List;
import java.util.UUID;

@Service
public class DefaultTranslationToTagService implements TranslationToTagService {

    private final TranslationToTagRepository translationToTagRepository;
    private final TranslationRepository translationRepository;
    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;

    private final BeanFactoryTransactionAttributeSourceAdvisor transactionAdvisor;

    public DefaultTranslationToTagService(TranslationToTagRepository translationToTagRepository, TranslationRepository translationRepository, ModelMapper modelMapper, TagRepository tagRepository,
                                          BeanFactoryTransactionAttributeSourceAdvisor transactionAdvisor) {
        this.translationToTagRepository = translationToTagRepository;
        this.translationRepository = translationRepository;
        this.modelMapper = modelMapper;
        this.tagRepository = tagRepository;
        this.transactionAdvisor = transactionAdvisor;
    }

    @Override
    @Transactional
    public TranslationToTagLinkResponse createLink(TranslationToTagLinkRequest translationToTagLinkRequest) {
        var translation = translationRepository.findById(translationToTagLinkRequest.getTranslationId()).orElseThrow(() -> new IllegalArgumentException("Translation id: " + translationToTagLinkRequest.getTranslationId() + "not found"));
        var tag = tagRepository.findById(translationToTagLinkRequest.getTagId()).orElseThrow(() -> new IllegalArgumentException("Tag id: " + translationToTagLinkRequest.getTranslationId() + "not found"));
        var translationTagLink = new TranslationToTagLink();
        translationTagLink.setTag(tag);
        translationTagLink.setTranslation(translation);
        translation.getTranslationToTagLinks().add(translationTagLink);
        tag.getTranslationToTagLinks().add(translationTagLink);
        return new TranslationToTagLinkResponse(tag.getId(), translation.getId());
    }

    @Override
    public TranslationToTagLinkResponse deleteLink(TranslationToTagLinkRequest translationToTagLinkRequest) {
        throw new UnsupportedOperationException("delete link not supported");
    }

    @Override
    @Transactional
    public List<TranslationResponseDto> getTagsTranslation(UUID tagId) {
        var translationIdsList = translationToTagRepository.findAllByTag_Id(tagId).stream()
                .map(e -> e.getTranslation().getId()).toList();

        return translationRepository.findAllById(translationIdsList).stream()
                .map(e -> modelMapper.map(e, TranslationResponseDto.class)).toList();
    }

    @Override
    @Transactional
    public List<TagResponseDto> getTranslationTags(UUID translationId) {
        var translationIdsList = translationToTagRepository.findAllByTranslation_Id(translationId).stream()
                .map(e -> e.getTag().getId()).toList();

        return tagRepository.findAllById(translationIdsList).stream()
                .map(e -> modelMapper.map(e, TagResponseDto.class)).toList();
    }
}
