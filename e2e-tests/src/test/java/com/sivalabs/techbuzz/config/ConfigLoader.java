package com.sivalabs.techbuzz.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.InputStream;

public class ConfigLoader {
    public static Configuration loadConfiguration() {
        ObjectMapper objectMapper = new ObjectMapper();
        String configFile = "config.json";
        try {
            String configFileOverride = System.getenv("CONFIG_FILE");
            System.out.println("configFileOverride:" + configFileOverride);
            if (configFileOverride != null && !configFileOverride.trim().isEmpty()) {
                configFile = configFileOverride;
            }
            InputStream inputStream = ConfigLoader.class.getClassLoader().getResourceAsStream(configFile);
            return objectMapper.readValue(inputStream, Configuration.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
