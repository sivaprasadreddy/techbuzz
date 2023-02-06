package com.sivalabs.techbuzz.notifications;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sivalabs.techbuzz.ApplicationProperties;
import com.sivalabs.techbuzz.common.exceptions.TechBuzzException;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
@ConditionalOnProperty(name = "techbuzz.email-provider", havingValue = "sendgrid")
@RequiredArgsConstructor
@Slf4j
public class SendGridEmailService implements EmailService {
    private final TemplateEngine templateEngine;
    private final ApplicationProperties properties;

    public void sendEmail(String template, Map<String, Object> params, String to, String subject) {
        try {
            Context context = new Context();
            context.setVariables(params);
            String content = templateEngine.process(template, context);

            Email from = new Email(properties.adminEmail());
            Email emailTo = new Email(to);
            Content emailContent = new Content("text/html", content);
            Mail mail = new Mail(from, subject, emailTo, emailContent);

            SendGrid sg = new SendGrid(properties.sendgridApiKey());
            Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            log.info("Sent verification email using SendGrid email service");
        } catch (Exception e) {
            throw new TechBuzzException("Error while sending verification email", e);
        }
    }
}
