package com.sivalabs.techbuzz.config;

import com.sivalabs.techbuzz.ApplicationProperties;
import com.sivalabs.techbuzz.notifications.EmailService;
import com.sivalabs.techbuzz.notifications.JavaEmailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.thymeleaf.TemplateEngine;

@Configuration
public class AppConfig {

    @Bean
    @ConditionalOnMissingBean
    public EmailService emailService(
            JavaMailSender javaMailSender,
            TemplateEngine templateEngine,
            ApplicationProperties properties) {
        return new JavaEmailService(javaMailSender, templateEngine, properties);
    }
}
