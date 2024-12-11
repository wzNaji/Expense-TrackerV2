package com.wzn.expensetrackerv2.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/api/bot")
public class BotController {

    private final WebClient webClient;

    public BotController(WebClient webClient, WebClient webClient1) {
        this.webClient = webClient1;
    }


    @GetMapping("")

}
