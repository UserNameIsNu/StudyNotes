package com.example.ch13;

import co.elastic.clients.elasticsearch._types.SortOptions;
import co.elastic.clients.elasticsearch._types.SortOptionsBuilders;
import co.elastic.clients.elasticsearch._types.SortOrder;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import com.example.ch13.document.Users;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.query.HighlightQuery;
import org.springframework.data.elasticsearch.core.query.highlight.Highlight;
import org.springframework.data.elasticsearch.core.query.highlight.HighlightField;

import java.util.List;

@SpringBootTest
@Slf4j
class Ch13ApplicationTests {
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    @Test
    void createIndex() {
        if(!elasticsearchOperations.indexOps(Users.class).exists()) {
            elasticsearchOperations.indexOps(Users.class).create();
            log.info("已创建");
        } else {
            log.info("已存在");
        }
    }

    @Test
    void deleteIndex() {
        if(elasticsearchOperations.indexOps(Users.class).exists()) {
            elasticsearchOperations.indexOps(Users.class).delete();
            log.info("已删除");
        } else {
            log.info("不存在");
        }
    }

    @Test
    void createMapping() {
        Document document = elasticsearchOperations.indexOps(Users.class).createMapping(Users.class);
        elasticsearchOperations.indexOps(Users.class).putMapping(document);
    }

    @Test
    void createDocument() {
        Users u1 = new Users("1", "张三", 20, "广东省珠海市香洲区");
        Users u2 = new Users("2", "李四", 21, "广东省珠海市唐家区");
        Users u3 = new Users("3", "王五", 22, "广东省广州市越秀区");
        Users u4 = new Users("4", "赵六", 23, "广东省珠海市香洲区");
        Users u5 = new Users("5", "珠海", 21, "广东省珠海市香洲区");
        elasticsearchOperations.save(u1, u2, u3, u4, u5);
    }

    @Test
    void deleteDocument() {
        elasticsearchOperations.delete("1", Users.class);
    }

    @Test
    void updateDocument() {
        elasticsearchOperations.update(new Users("1", "张三芜", 20, "广东省珠海市香洲区"));
    }

    @Test
    void selectDocumentById() {
        Users users = elasticsearchOperations.get("1", Users.class);
        log.info(users.toString());
    }

    @Test
    void selectDocument() {
        NativeQuery nativeQuery = NativeQuery.builder().build();
        SearchHits<Users> hits = elasticsearchOperations.search(nativeQuery, Users.class);
        hits.getSearchHits().forEach(hit -> {
            Users user = hit.getContent();
            log.info(user.toString());
        });
    }

    @Test
    void pageSelectDocument() {
        NativeQuery query = NativeQuery.builder()
                .withPageable(PageRequest.of(0, 2))
                .build();
        SearchHits<Users> hits = elasticsearchOperations.search(query, Users.class);
        //循环遍历命中的结果集
        hits.getSearchHits().forEach(hit -> {
            Users user = hit.getContent();
            log.info(user.getName());
        });
    }

    @Test
    void sortSelectDocuments() {
        //构建排序选项（SortOptions）
        SortOptions sortOptions = SortOptionsBuilders
                .field(builder -> builder.field("age").order(SortOrder.Desc));
        //创建NativeQuery并设置排序选项
        NativeQuery query = NativeQuery.builder().withSort(sortOptions).build();
        SearchHits<Users> hits = elasticsearchOperations.search(query, Users.class);
        //循环遍历命中的结果集
        hits.getSearchHits().forEach(hit -> {
            Users user = hit.getContent();
            log.info(user.getName() + " : {}", user.getAge());
        });
    }

    @Test
    void termSelectDocument() {
        NativeQueryBuilder queryBuilder = NativeQuery.builder();
        queryBuilder.withQuery(query ->
                query.term(trem ->
                        trem.field("birthplace").value("广州")));
        //执行查询并返回命中的结果
        SearchHits<Users> hits = elasticsearchOperations.search(queryBuilder.build(), Users.class);
        //循环遍历命中的结果集
        hits.getSearchHits().forEach(hit -> {
            Users user = hit.getContent();
            log.info(user.getName() + " : {}", user.getBirthplace());
        });
    }

    @Test
    void matchSelectDocument() {
        NativeQueryBuilder queryBuilder = NativeQuery.builder();
        queryBuilder.withQuery(query ->
                query.match(match ->
                        match.field("birthplace").query("广州")));
        //执行查询并返回命中的结果
        SearchHits<Users> hits = elasticsearchOperations.search(queryBuilder.build(), Users.class);
        //循环遍历命中的结果集
        hits.getSearchHits().forEach(hit -> {
            Users user = hit.getContent();
            log.info(user.getName() + " : {}", user.getBirthplace());
        });
    }

    @Test
    void booleanSelectDocument() {
        NativeQueryBuilder queryBuilder = NativeQuery.builder();
        queryBuilder.withQuery(query ->
                query.bool(bool ->
                        bool.should(
                                QueryBuilders.term(term -> term.field("birthplace").value("珠海")),
                                QueryBuilders.term(term -> term.field("name").value("珠海"))
                        )
                ));
        //执行查询并返回命中的结果
        SearchHits<Users> hits = elasticsearchOperations.search(queryBuilder.build(), Users.class);
        //循环遍历命中的结果集
        hits.getSearchHits().forEach(hit -> {
            Users user = hit.getContent();
            log.info(user.getName() + " : {}", user.getBirthplace());
        });
    }

    @Test
    void lightSelectDocument() {
        NativeQueryBuilder queryBuilder = NativeQuery.builder();
        //构建高亮字段
        HighlightField birthplace = new HighlightField("birthplace");
        //封装成高亮对象
        Highlight highlight = new Highlight(List.of(birthplace));
        //执行高亮查询
        queryBuilder.withQuery(query ->
                query.term(term -> term.field("birthplace").value("广东省"))
        ).withHighlightQuery(new HighlightQuery(highlight, Users.class));
        //返回查询的命中结果
        SearchHits<Users> hits = elasticsearchOperations.search(queryBuilder.build(), Users.class);
        hits.getSearchHits().forEach(hit -> {
            //获取高亮的结果
            String highBirthplace = hit.getHighlightField("birthplace").getFirst();
            hit.getContent().setBirthplace(highBirthplace);
            Users user = hit.getContent();
            log.info(user.getName() + " : {}", user.getBirthplace());
        });
    }
}
