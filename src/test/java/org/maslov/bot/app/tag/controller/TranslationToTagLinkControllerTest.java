package org.maslov.bot.app.tag.controller;

import org.junit.jupiter.api.Test;
import org.maslov.bot.app.tag.dto.TagDto;
import org.maslov.bot.app.tag.dto.TagResponseDto;
import org.maslov.bot.app.tag.dto.request.TranslationToTagLinkRequest;
import org.maslov.bot.app.tag.dto.response.TranslationResponse;
import org.maslov.bot.app.tag.dto.response.TranslationToTagLinkResponse;
import org.maslov.bot.app.utils.CustomPageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.net.URI;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class TranslationToTagLinkControllerTest {

    @Container // Manages the container lifecycle
    @ServiceConnection // Automatically configures Spring datasource properties
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.3");

    @Autowired
    private TestRestTemplate restTemplate;


    private ResponseEntity<TagResponseDto> createTestTag(String name, String description) {
        TagDto tagDto = TagDto.builder().name(name).description(description).build();

        URI uri = UriComponentsBuilder.fromPath(TagController.ROOT_URL)
                .build().toUri();
        ResponseEntity<TagResponseDto> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                new HttpEntity<>(tagDto),
                new ParameterizedTypeReference<TagResponseDto>() {
                }
        );
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        assertNotNull(responseEntity.getBody().getDescription());
        assertEquals(responseEntity.getBody().getName(), name);
        return responseEntity;
    }

    @Test
    void createLink() {
        var testTagResp = createTestTag("testTag", "testTagDescription");
        int page = 0;
        int size = 5;
        URI uri = UriComponentsBuilder.fromPath(TranslationController.ROOT_URL)
                .queryParam("page", page)
                .queryParam("size", size)
                .build()
                .toUri();
        ResponseEntity<CustomPageImpl<TranslationResponse>> translationResponse = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CustomPageImpl<TranslationResponse>>() {
                } // Custom type reference
        );
        var translations = translationResponse.getBody().getContent();
        TranslationToTagLinkRequest translationToTagLinkRequest = TranslationToTagLinkRequest.builder()
                .tagId(testTagResp.getBody().getId())
                .translationId(translations.get(0).getId())
                .build();

        uri = UriComponentsBuilder.fromPath(TranslationToTagLinkController.ROOT_URL)
                .build().toUri();
        ResponseEntity<TranslationToTagLinkResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                new HttpEntity<>(translationToTagLinkRequest),
                new ParameterizedTypeReference<TranslationToTagLinkResponse>() {
                }
        );
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        assertNotNull(responseEntity.getBody().getTagId());
        assertNotNull(responseEntity.getBody().getTranslationId());
        //assertEquals(responseEntity.getBody().getWord(), "cosines");
    }

    @Test
    void deleteLink() {
    }

    @Test
    void getTagsTranslation() {
    }

    @Test
    void getTranslationTags() {
    }
}