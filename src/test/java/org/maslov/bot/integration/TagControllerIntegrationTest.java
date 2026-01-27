package org.maslov.bot.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Testcontainers
public class TagControllerIntegrationTest {


    @Container // Manages the container lifecycle
    @ServiceConnection // Automatically configures Spring datasource properties
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:16-alpine"); // Use a specific version

    @Test
    void contextLoads() {
        // This test simply verifies that the Spring context loads correctly
        // and the database connection is established via Testcontainers
        assertThat(postgres.isRunning()).isTrue();
        // You can add repository tests here (e.g., save and retrieve data)
    }
}
