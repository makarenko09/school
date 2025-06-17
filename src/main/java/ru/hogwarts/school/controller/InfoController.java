package ru.hogwarts.school.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.configuration.InfoService;

@RestController
@RequestMapping("/port")
public class InfoController {

    private final InfoService InfoService;

    public InfoController(InfoService portProvider) {
        this.InfoService = portProvider;
    }

    @GetMapping("/get-int-improve-with-streamAPI")
    public void doingSomethingAfter() {
        InfoService.calculateSomeAnyInt();
    }

    @GetMapping
    public String info() {
        return "Server port: " + InfoService.getPort();
    }

}
