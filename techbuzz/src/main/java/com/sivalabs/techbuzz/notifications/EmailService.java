package com.sivalabs.techbuzz.notifications;

import java.util.Map;

public interface EmailService {
    void sendEmail(String template, Map<String, Object> params, String to, String subject);
}
