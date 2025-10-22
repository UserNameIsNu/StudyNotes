package com.example.ch13_homework.service;

import com.example.ch13_homework.entity.News;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsService {
    List<News> connTest();

    // 发布新闻
    void pushNews(String title, String author, String classification, String text);

    // 获取ID
    Integer getIdByAllField(String title, String author, String classification, String text);

    // 修改新闻
    void updNews(Integer id, String title, String author, String classification, String text);

    // 删除新闻
    void delNews(Integer id, Class<?> clazz);

    // 搜索新闻
    SearchHits<News> searchNews(String title, String author, String classification, String text);

    // 获取新闻
    News getNewsById(Integer id);
}
