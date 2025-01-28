package com.example.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {

    private final Properties properties;

    public ConfigLoader() {
        properties = new Properties();
        try (InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) {
                throw new RuntimeException("Unable to find config.properties");
            }
            properties.load(input);
        } catch (IOException ex) {
            throw new RuntimeException("Failed to load configuration", ex);
        }
    }

    public String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public String getClientId() {
        return properties.getProperty("spotify.clientId");
    }

    public String getClientSecret() {
        return properties.getProperty("spotify.clientSecret");
    }
}
