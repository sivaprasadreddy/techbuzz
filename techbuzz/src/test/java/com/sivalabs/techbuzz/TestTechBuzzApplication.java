package com.sivalabs.techbuzz;

import org.springframework.boot.SpringApplication;

public class TestTechBuzzApplication {
    public static void main(String[] args) {
        TestcontainersConfig.init();

        SpringApplication.from(TechBuzzApplication::main)
                .with(TestcontainersConfig.class)
                .run(args);
    }
}
