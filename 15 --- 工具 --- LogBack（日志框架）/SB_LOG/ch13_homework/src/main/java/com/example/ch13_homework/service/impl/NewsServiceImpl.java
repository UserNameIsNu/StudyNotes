package com.example.ch13_homework.service.impl;

import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.example.ch13_homework.entity.News;
import com.example.ch13_homework.mapper.NewsMapper;
import com.example.ch13_homework.service.NewsService;
import com.example.ch13_homework.service.mq.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsServiceImpl implements NewsService {

    private final NewsMapper newsMapper;

    private final Producer producer;

    private final ElasticsearchOperations elasticsearchOperations;

    @Override
    public List<News> connTest() {
        return newsMapper.connTest();
    }

    @Override
    public void pushNews(String title, String author, String classification, String text) {
        // 加入数据库
        newsMapper.pushNews(title, author,classification, text);
        // 加入MQ
        producer.push(new News(getIdByAllField(title, author, classification, text), title, author, classification, text));
    }

    @Override
    public Integer getIdByAllField(String title, String author, String classification, String text) {
        return newsMapper.getIdByAllField(title, author, classification, text);
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

    @Override
    public SearchHits<News> searchNews(String title, String author, String classification, String text) {
        NativeQueryBuilder queryBuilder = NativeQuery.builder();
        queryBuilder.withQuery(query ->
                query.bool(bool ->
                        bool.should(
                                QueryBuilders.term(term -> term.field("title").value(title)),
                                QueryBuilders.term(term -> term.field("author").value(author)),
                                QueryBuilders.term(term -> term.field("classification").value(classification)),
                                QueryBuilders.term(term -> term.field("text").value(text))
                        )
                ));
        //执行查询并返回命中的结果
        return elasticsearchOperations.search(queryBuilder.build(), News.class);
    }

    @Override
    public News getNewsById(Integer id) {
        return newsMapper.getNewsById(id);
    }
}
