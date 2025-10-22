package com.example.ch13_homework.mapper;

import com.example.ch13_homework.entity.News;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NewsMapper {
    List<News> connTest();

    // 发布新闻
    void pushNews(@Param("title") String title, @Param("author") String author, @Param("classification") String classification, @Param("text") String text);

    // 获取ID，根据其它所有字段
    Integer getIdByAllField(@Param("title") String title, @Param("author") String author, @Param("classification") String classification, @Param("text") String text);

    // 获取对象，根据ID
    News getNewsById(@Param("id") Integer id);
}
