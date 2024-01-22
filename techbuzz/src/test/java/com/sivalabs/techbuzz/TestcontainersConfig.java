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
    PostgreSQLContainer<?> postgreSQLContainer() {
        return new PostgreSQLContainer<>("postgres:16.1-alpine");
    }

    /*@Bean
    GenericContainer<?> mailhogContainer(DynamicPropertyRegistry registry) {
        var container = new GenericContainer("mailhog/mailhog").withExposedPorts(1025, 8025);
        registry.add("spring.mail.host", container::getHost);
        registry.add("spring.mail.port", () -> String.valueOf(container.getMappedPort(1025)));
        return container;
    }*/

    // JavaMailSender bean is getting initialized even before mailhogContainer() bean is configured.
    // So, the dynamic mail server host and port are not properly configured and using the default config.
    // Temporary fix: Spin up mail server and set spring mail properties.
    public static void init() {
        var container = new GenericContainer("mailhog/mailhog").withExposedPorts(1025, 8025);
        container.start();
        System.setProperty("spring.mail.host", container.getHost());
        System.setProperty("spring.mail.port", String.valueOf(container.getMappedPort(1025)));
    }
}
