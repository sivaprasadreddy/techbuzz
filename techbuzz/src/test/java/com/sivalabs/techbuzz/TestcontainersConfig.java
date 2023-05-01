package com.sivalabs.techbuzz;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfig {
    @Bean
    @ServiceConnection
    public PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:15.2-alpine");
    }

    static GenericContainer<?> mailhog = new GenericContainer("mailhog/mailhog").withExposedPorts(1025);

    static {
        mailhog.start();
        System.setProperty("spring.mail.host", mailhog.getHost());
        System.setProperty("spring.mail.port", String.valueOf(mailhog.getMappedPort(1025)));
    }
}
