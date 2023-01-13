package com.sivalabs.techbuzz.common;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles({ "test", "integration-test" })
@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public abstract class AbstractIntegrationTest {

	@Autowired
	protected MockMvc mockMvc;

	@Autowired
	protected ObjectMapper objectMapper;

	static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.1-alpine");

	@LocalServerPort
	private Integer port;

	@BeforeEach
	void setUp() {
		RestAssured.baseURI = "http://localhost:" + port;
	}

	@DynamicPropertySource
	static void postgresProperties(DynamicPropertyRegistry registry) {
		Startables.deepStart(postgres).join();

		registry.add("spring.datasource.url", postgres::getJdbcUrl);
		registry.add("spring.datasource.username", postgres::getUsername);
		registry.add("spring.datasource.password", postgres::getPassword);
	}

}
