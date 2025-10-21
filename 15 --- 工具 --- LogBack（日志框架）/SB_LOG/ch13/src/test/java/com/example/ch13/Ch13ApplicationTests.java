package com.example.ch13;

import com.example.ch13.document.Users;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;

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
}
