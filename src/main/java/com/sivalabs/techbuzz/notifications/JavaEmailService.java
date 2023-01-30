package com.sivalabs.techbuzz.notifications;

import com.sivalabs.techbuzz.ApplicationProperties;
import com.sivalabs.techbuzz.common.exceptions.TechBuzzException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

@RequiredArgsConstructor
@Slf4j
public class JavaEmailService implements EmailService {
    private final JavaMailSender emailSender;
    private final ApplicationProperties properties;

    public void sendEmail(String to, String subject, String content) {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(properties.adminEmail());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            emailSender.send(mimeMessage);
            log.info("Sent verification email using default email service");
        } catch (Exception e) {
            throw new TechBuzzException("Error while sending verification email", e);
        }
    }
}
