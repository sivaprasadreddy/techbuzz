package com.sivalabs.techbuzz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class TechBuzzApplication {

	public static void main(String[] args) {
		SpringApplication.run(TechBuzzApplication.class, args);
	}

}
