package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.AppConfig;

@RestController("/port")
public class InfoController {

    private final AppConfig appConfig;

    public InfoController(AppConfig appConfig) {
        this.appConfig = appConfig;
    }

    @GetMapping
    public String info() {
        return "Server port: " + appConfig.getPort();
    }

}
