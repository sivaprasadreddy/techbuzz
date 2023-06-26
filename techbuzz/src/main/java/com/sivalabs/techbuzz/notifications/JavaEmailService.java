package com.sivalabs.techbuzz.notifications;

import com.sivalabs.techbuzz.ApplicationProperties;
import com.sivalabs.techbuzz.common.exceptions.TechBuzzException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class JavaEmailService implements EmailService {
    private static final Logger log = LoggerFactory.getLogger(JavaEmailService.class);

    private final JavaMailSender emailSender;
    private final TemplateEngine templateEngine;
    private final ApplicationProperties properties;

    public JavaEmailService(
            JavaMailSender emailSender, TemplateEngine templateEngine, ApplicationProperties properties) {
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.properties = properties;
    }

    public void sendEmail(String template, Map<String, Object> params, String to, String subject) {

        sendEmail(template, params, to, subject, false);
    }

    @Override
    public void sendBroadcastEmail(String template, Map<String, Object> params, String recipient, String subject) {

        sendEmail(template, params, recipient, subject, true);
    }

    private void sendEmail(String template, Map<String, Object> params, String recipient, String subject, boolean broadcast) {
        try {
            Context context = new Context();
            context.setVariables(params);
            String content = templateEngine.process(template, context);
            MimeMessage mimeMessage = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
            helper.setFrom(properties.adminEmail());
            if (broadcast) helper.setBcc(InternetAddress.parse(recipient));
            else helper.setTo(recipient);
            helper.setSubject(subject);
            helper.setText(content, true);
            emailSender.send(mimeMessage);
            log.info("Sent email using default email service");
        } catch (Exception e) {
            throw new TechBuzzException("Error while sending email", e);
        }
    }
}
