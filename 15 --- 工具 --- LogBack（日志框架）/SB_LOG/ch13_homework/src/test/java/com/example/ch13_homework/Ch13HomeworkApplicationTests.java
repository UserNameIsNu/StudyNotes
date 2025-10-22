package com.example.ch13_homework;

import com.example.ch13_homework.entity.News;
import com.example.ch13_homework.service.NewsService;
import com.example.ch13_homework.service.impl.NewsServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@Slf4j
class Ch13HomeworkApplicationTests {
	@Autowired
	private NewsService service;

	@Autowired
	private ElasticsearchOperations elasticsearchOperations;

	@Autowired
	private RedisTemplate redisTemplate;

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
	 * 修改新闻（没有同步数据库）
	 */
	@Test
	void updNews() {
		service.updNews(18, "震惊！502竟然发生了这种事！", "龚靖皓", "游戏", "你怎么知道我是天才少年和钻石大亨？");
	}

	/**
	 * 删除新闻（没有同步数据库）
	 */
	@Test
	void delNews() {
		service.delNews(18, News.class);
	}

	/**
	 * 搜索新闻
	 */
	@Test
	void searchNews() {
		SearchHits<News> hits = service.searchNews("", "", "", "天才");
		// 输出看看
		hits.getSearchHits().forEach(hit -> {
			News news = hit.getContent();
			log.info(news.toString());
		});
		log.info("test");
	}

	/**
	 * 搜索新闻
	 */
	@Test
	void getNewsFormRedis() {
		// 根据ID先去Redis里面找
		// 有就返回
		// 没有就从数据库返回，顺便往Redis里存一下
		// Redis懒得加过期了
		Integer id = 19;
		String key = "news:" + id;

		// 现在会返回可读的 Map
		Map<Object, Object> cachedNews = redisTemplate.opsForHash().entries(key);

		if (cachedNews != null && !cachedNews.isEmpty()) {
			System.out.println("从Redis获取: " + cachedNews);
			return;
		}

		News newsFromDB = service.getNewsById(id);
		if (newsFromDB != null) {
			Map<String, String> newsMap = new HashMap<>();
			newsMap.put("title", newsFromDB.getTitle());
			newsMap.put("author", newsFromDB.getAuthor());
			newsMap.put("classification", newsFromDB.getClassification());
			newsMap.put("text", newsFromDB.getText());

			redisTemplate.opsForHash().putAll(key, newsMap);
			System.out.println("从数据库获取并缓存: " + newsFromDB);
		}
	}
}
