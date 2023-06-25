package com.sivalabs.techbuzz;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "techbuzz")
@Validated
public record ApplicationProperties(
        String emailProvider,
        @NotEmpty @Email String adminEmail,
        String sendgridApiKey,
        @Min(1) int postsPerPage,
        List<String> importFilePaths,
        int newPostsAgeInDays,
        String newPostsNotificationFrequency) {}
