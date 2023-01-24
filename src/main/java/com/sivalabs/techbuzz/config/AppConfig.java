package com.sivalabs.techbuzz.config;

import com.sivalabs.techbuzz.notifications.EmailService;
import com.sivalabs.techbuzz.notifications.JavaEmailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;

@Configuration
public class AppConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public RedirectStrategy getRedirectStrategy() {
		return new DefaultRedirectStrategy();
	}

	@Bean
	@ConditionalOnMissingBean
	public EmailService emailService(JavaMailSender javaMailSender) {
		return new JavaEmailService(javaMailSender);
	}
}
