package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.configuration.PortProvider;

@RestController
@RequestMapping("/port")
public class InfoController {

    private final PortProvider portProvider;

    public InfoController(PortProvider portProvider) {
        this.portProvider = portProvider;
    }

    @GetMapping
    public String info() {
        return "Server port: " + portProvider.getPort();
    }
}
