package com.example.ch13_homework.service.impl;

import com.example.ch13_homework.entity.News;
import com.example.ch13_homework.mapper.NewsMapper;
import com.example.ch13_homework.service.NewsService;
import com.example.ch13_homework.service.mq.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;

    private final Producer producer;

    @Override
    public List<News> connTest() {
        return newsMapper.connTest();
    }

    @Override
    public void pushNews(String title, String author, String classification, String text) {
        // 加入数据库
        newsMapper.pushNews(title, author,classification, text);
        // 加入MQ
        producer.push(new News(null, title, author, classification, text));
    }

    @Override
    public void updNews(Integer id, String title, String author, String classification, String text) {
        // 加入MQ
        producer.upd(new News(id, title, author, classification, text));
    }

    @Override
    public void delNews(Integer id, Class<?> clazz) {
        Object[] objects = new Object[]{id, clazz};
        // 加入MQ
        producer.del(objects);
    }
}
