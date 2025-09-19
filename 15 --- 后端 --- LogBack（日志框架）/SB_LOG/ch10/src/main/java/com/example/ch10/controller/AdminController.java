package com.example.ch10.controller;

import com.example.ch10.service.PublishService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController {
    private PublishService service;

    public AdminController(PublishService service) {
        this.service = service;
    }

    @PostMapping("/message")
    public String sendTopic(@RequestParam("message") String message) {
        service.sendTopicMessage(message);
        return "success";
    }

    @PostMapping("/sport")
    public String sendSport(@RequestParam("message") String message) {
        service.sendSportMessage(message);
        return "success";
    }
}
