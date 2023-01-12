package com.sivalabs.techbuzz;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "techbuzz")
public record ApplicationProperties(boolean importDataEnabled, List<String> importFilePaths) {}
