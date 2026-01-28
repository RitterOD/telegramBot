package org.maslov.bot.app.tag.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.maslov.bot.app.model.LangCode;
import org.maslov.bot.app.tag.dto.request.TranslationRequest;
import org.maslov.bot.app.tag.dto.response.TranslationResponse;
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
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class TranslationControllerTest {

    @Container // Manages the container lifecycle
    @ServiceConnection // Automatically configures Spring datasource properties
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16.3"); // Use a specific version

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void contextLoads() {
        // This test simply verifies that the Spring context loads correctly
        // and the database connection is established via Testcontainers
        assertThat(postgres.isRunning()).isTrue();
        // You can add repository tests here (e.g., save and retrieve data)
    }

    @Test
    @DisplayName("Read first page of translations")
    void getAll() {
        int page = 0;
        int size = 5;
        URI uri = UriComponentsBuilder.fromPath(TranslationController.ROOT_URL)
                .queryParam("page", page)
                .queryParam("size", size)
                .build()
                .toUri();
        ResponseEntity<CustomPageImpl<TranslationResponse>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CustomPageImpl<TranslationResponse>>() {
                } // Custom type reference
        );
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        var pageResult = responseEntity.getBody();
        assertThat(pageResult).isNotNull();
        assertThat(!pageResult.getContent().isEmpty());
    }

    @Test
    void getOne() {
        URI uri = UriComponentsBuilder.fromPath(TranslationController.ROOT_URL)
                .pathSegment("{id}")
                .buildAndExpand(Map.of("id", "16c9216d-c2ab-4bcc-b571-f39c7dcfe017"))
                .encode()
                .toUri();
        ResponseEntity<TranslationResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<TranslationResponse>() {
                } // Custom type reference
        );
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
    }

    @Test
    void getMany() {
    }

    @Test
    void create() {
        TranslationRequest translationRequest = TranslationRequest.builder()
                .from(LangCode.ENG)
                .to(LangCode.RU)
                .word("cosines")
                .translation("уют")
                .build();

        URI uri = UriComponentsBuilder.fromPath(TranslationController.ROOT_URL)
                .build().toUri();
        ResponseEntity<TranslationResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                new HttpEntity<>(translationRequest),
                new ParameterizedTypeReference<TranslationResponse>() {
                }
        );
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        assertNotNull(responseEntity.getBody().getWord());
        assertEquals(responseEntity.getBody().getWord(), "cosines");
    }

    @Test
    void patch() {
    }

    @Test
    void patchMany() {
    }

    @Test
    void delete() {
    }

    @Test
    void deleteMany() {
    }
}