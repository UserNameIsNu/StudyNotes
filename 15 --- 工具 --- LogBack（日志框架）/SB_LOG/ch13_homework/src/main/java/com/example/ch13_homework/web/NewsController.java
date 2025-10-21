package com.example.ch13_homework.web;

import com.example.ch13_homework.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class NewsController {
    private final NewsService newsService;

    @GetMapping("/test")
    public String connTest() {
        newsService.connTest();
        return "11";
    }
}
