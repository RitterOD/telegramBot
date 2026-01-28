package org.maslov.bot.integration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.maslov.bot.app.model.Translation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.web.PagedModel;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@Testcontainers
public class TagControllerIntegrationTest {


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
    void readTranslations() {
        // Define pagination parameters
        int page = 0;
        int size = 5;
        String url = "/api/items?page=" + page + "&size=" + size;

        // Use exchange() to handle the complex generic type (Page<Item>)
        ResponseEntity<PagedModel<Translation>> responseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PagedModel<Translation>>() {} // Custom type reference
        );

        // Assertions
        assertThat(responseEntity.getStatusCode().is2xxSuccessful()).isTrue();
        PagedModel<Translation> pageResult = responseEntity.getBody();
        assertThat(pageResult).isNotNull();
        assertThat(!pageResult.getContent().isEmpty());
    }
}
