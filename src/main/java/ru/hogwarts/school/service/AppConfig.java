package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
@Value("${server.port}")
    private Integer port;

    public Integer getPort() {
        return port;
    }
}
