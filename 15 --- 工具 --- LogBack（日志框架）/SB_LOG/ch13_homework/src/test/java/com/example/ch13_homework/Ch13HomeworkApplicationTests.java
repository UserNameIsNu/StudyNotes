package com.example.ch13_homework;

import com.example.ch13_homework.entity.News;
import com.example.ch13_homework.service.NewsService;
import com.example.ch13_homework.service.impl.NewsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.document.Document;

@SpringBootTest
@Slf4j
class Ch13HomeworkApplicationTests {
	@Autowired
	private NewsService service;

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	/**
	 * 测试数据库连通性
	 */
	@Test
	void contextLoads() {
		System.out.println(service.connTest());
	}

	/**
	 * 初始化ES的索引和映射
	 */
	@Test
	void load() {
		// 索引
		if(!elasticsearchOperations.indexOps(News.class).exists()) {
			elasticsearchOperations.indexOps(News.class).create();
			log.info("已创建");
		} else {
			log.info("已存在");
		}
		// 映射
		Document document = elasticsearchOperations.indexOps(News.class).createMapping(News.class);
		elasticsearchOperations.indexOps(News.class).putMapping(document);
	}








	/**
	 * 发布新闻
	 */
	@Test
	void pushNews() {
		service.pushNews("震惊！502竟然发生了这种事！", "龚靖皓", "游戏", "你怎么知道我是天才少年？");
	}

	/**
	 * 修改新闻
	 */
	@Test
	void updNews() {
		service.updNews("cCKjBpoBYjlpgGOmQfjH", "震惊！502竟然发生了这种事！", "龚靖皓", "游戏", "你怎么知道我是天才少年和钻石大亨？");
	}

	/**
	 * 删除新闻
	 */
	@Test
	void delNews() {
		service.delNews(11, News.class);
	}
}
