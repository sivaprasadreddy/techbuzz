package com.sivalabs.techbuzz;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfig {

    @Bean
    @ServiceConnection
    PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:15.2-alpine");
    }

    @Bean
    GenericContainer<?> mailhogContainer(DynamicPropertyRegistry registry) {
        var container = new GenericContainer("mailhog/mailhog").withExposedPorts(1025);
        registry.add("spring.mail.host", container::getHost);
        registry.add("spring.mail.port", () -> String.valueOf(container.getMappedPort(1025)));
        return container;
    }
}
