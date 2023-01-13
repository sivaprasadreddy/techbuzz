package com.sivalabs.techbuzz;

import com.sivalabs.techbuzz.common.AbstractIntegrationTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

class TechBuzzApplicationTests extends AbstractIntegrationTest {

	@Test
	void contextLoads() {
		given().contentType(ContentType.JSON).when().get("/actuator/health").then().statusCode(200);
	}

}
