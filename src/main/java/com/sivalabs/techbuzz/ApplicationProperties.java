package com.sivalabs.techbuzz;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@ConfigurationProperties(prefix = "techbuzz")
@Validated
public record ApplicationProperties(@NotEmpty @Email String adminEmail, @Min(1) int postsPerPage, List<String> importFilePaths) {
}
