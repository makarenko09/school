package ru.hogwarts.school.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig implements PortProvider {
@Value("${server.port}")
    private Integer port;

    @Override
    public Integer getPort() {
        return port;
    }
}
