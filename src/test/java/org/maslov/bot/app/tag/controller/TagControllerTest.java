package org.maslov.bot.app.tag.controller;

import org.junit.jupiter.api.Test;
import org.maslov.bot.app.tag.dto.TagDto;
import org.maslov.bot.app.tag.dto.TagResponseDto;
import org.maslov.bot.app.tag.dto.request.TagLinkRequest;
import org.maslov.bot.app.tag.dto.response.TagLinkResponse;
import org.maslov.bot.app.utils.CustomPageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Slice;
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
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class TagControllerTest {

    public static final String TEST_TAG_1 = "TestTag_1";
    public static final String TEST_TAG_2 = "TestTag_2";
    public static final String TEST_TAG_PREFIX = "TestTag_";

    public static final String TEST_TAG_DESCRIPTION_PREFIX = "Description for integration testing of TestTag_";
    public static final String TEST_TAG_1_DESCRIPTION = "Test tag 1 for integration testing";
    public static final String TEST_TAG_2_DESCRIPTION = "Test tag 2 for integration testing";
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

    private void insertTags(int number) {
        for(int i = 0; i < number; ++i) {
            createTestTag(TEST_TAG_PREFIX + i, TEST_TAG_DESCRIPTION_PREFIX + i);
        }
    }


    @Test
    void createTag() {
        createTestTag(TEST_TAG_1, TEST_TAG_1_DESCRIPTION);
    }

    @Test
    void getAll() {
        insertTags(5);
        int page = 0;
        int size = 5;
        URI uri = UriComponentsBuilder.fromPath(TagController.ROOT_URL)
                .queryParam("page", page)
                .queryParam("size", size)
                .build()
                .toUri();
        ResponseEntity<CustomPageImpl<TagResponseDto>> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<CustomPageImpl<TagResponseDto>>() {
                } // Custom type reference
        );
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        var pageResult = responseEntity.getBody();
        assertThat(pageResult).isNotNull();
        assertThat(!pageResult.getContent().isEmpty());
    }

    @Test
    void get() {
    }

    @Test
    void delete() {
    }

    @Test
    void createTagToTagLink() {
        var tag_1_response = createTestTag(TEST_TAG_1, TEST_TAG_1_DESCRIPTION);
        var tag_2_response = createTestTag(TEST_TAG_2, TEST_TAG_2_DESCRIPTION);
        TagLinkRequest tagLinkRequest = TagLinkRequest.builder()
                .parentId(tag_1_response.getBody().getId())
                .childId(tag_2_response.getBody().getId())
                .build();
        URI uri = UriComponentsBuilder.fromPath(TagController.ROOT_URL).pathSegment("link")
                .build().toUri();
        ResponseEntity<TagLinkResponse> responseEntity = restTemplate.exchange(
                uri,
                HttpMethod.POST,
                new HttpEntity<>(tagLinkRequest),
                new ParameterizedTypeReference<TagLinkResponse>() {
                }
        );
        assertEquals(tag_1_response.getBody().getId(), responseEntity.getBody().getParentId());
        assertEquals(tag_2_response.getBody().getId(), responseEntity.getBody().getChildId());

    }
}