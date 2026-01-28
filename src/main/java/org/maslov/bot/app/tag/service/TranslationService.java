package org.maslov.bot.app.tag.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.maslov.bot.app.dao.repository.TranslationRepository;
import org.maslov.bot.app.model.Translation;
import org.maslov.bot.app.tag.dto.request.TranslationRequest;
import org.maslov.bot.app.tag.dto.response.TranslationResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class TranslationService {

    private final TranslationRepository translationRepository;

    private final ObjectMapper objectMapper;

    private final ModelMapper modelMapper;

    public TranslationService(TranslationRepository translationRepository, ObjectMapper objectMapper, ModelMapper modelMapper) {
        this.translationRepository = translationRepository;
        this.objectMapper = objectMapper;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public Page<TranslationResponse> getAll(Pageable pageable) {
        return translationRepository.findAll(pageable).map(entity -> modelMapper.map(entity, TranslationResponse.class));
    }

    @Transactional
    public TranslationResponse getOne(UUID id) {
        Optional<Translation> translationOptional = translationRepository.findById(id);
        return modelMapper.map(translationOptional.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id))), TranslationResponse.class);
    }

    @Transactional
    public List<TranslationResponse> getMany(List<UUID> ids) {
        return translationRepository.findAllById(ids).stream().map(e -> modelMapper.map(e, TranslationResponse.class)).toList();
    }

    @Transactional
    public TranslationResponse create(TranslationRequest translation) {
        return modelMapper.map(translationRepository.save(modelMapper.map(translation, Translation.class)), TranslationResponse.class);
    }

    @Transactional
    public TranslationResponse patch(UUID id, JsonNode patchNode) throws IOException {
        Translation translation = translationRepository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Entity with id `%s` not found".formatted(id)));

        objectMapper.readerForUpdating(translation).readValue(patchNode);

        return modelMapper.map(translationRepository.save(translation), TranslationResponse.class);
    }

    @Transactional
    public List<UUID> patchMany(List<UUID> ids, JsonNode patchNode) throws IOException {
        Collection<Translation> translations = translationRepository.findAllById(ids);

        for (Translation translation : translations) {
            objectMapper.readerForUpdating(translation).readValue(patchNode);
        }

        List<Translation> resultTranslations = translationRepository.saveAll(translations);
        return resultTranslations.stream()
                .map(Translation::getId)
                .toList();
    }

    @Transactional
    public TranslationResponse delete(UUID id) {
        Translation translation = translationRepository.findById(id).orElse(null);
        if (translation != null) {
            translationRepository.delete(translation);
        }
        return modelMapper.map(translation, TranslationResponse.class);
    }

    public void deleteMany(List<UUID> ids) {
        translationRepository.deleteAllById(ids);
    }
}
