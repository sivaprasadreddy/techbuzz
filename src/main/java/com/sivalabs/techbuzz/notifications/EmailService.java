package com.sivalabs.techbuzz.notifications;

public interface EmailService {
    void sendEmail(String to, String subject, String content);
}
