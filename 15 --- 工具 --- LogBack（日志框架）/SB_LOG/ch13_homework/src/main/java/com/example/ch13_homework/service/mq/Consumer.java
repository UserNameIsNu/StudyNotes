package com.example.ch13_homework.service.mq;

import com.example.ch13_homework.config.RabbitDeleteConfig;
import com.example.ch13_homework.config.RabbitPushConfig;
import com.example.ch13_homework.config.RabbitUpdateConfig;
import com.example.ch13_homework.entity.News;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Consumer {
    private final ElasticsearchOperations elasticsearchOperations;

    public Consumer(ElasticsearchOperations elasticsearchOperations) {
        this.elasticsearchOperations = elasticsearchOperations;
    }

    /**
     * 新的新闻
     * @param news
     */
    @RabbitListener(queues = RabbitPushConfig.PUSH_QUEUE_NAME)
    public void push(News news) {
        System.out.println(news);
        elasticsearchOperations.save(news);
    }

    /**
     * 修改某个
     * @param news
     */
    @RabbitListener(queues = RabbitUpdateConfig.UPD_QUEUE_NAME)
    public void upd(News news) {
        System.out.println(news);
        elasticsearchOperations.update(new News(
                news.getId(),
                news.getTitle(),
                news.getAuthor(),
                news.getClassification(),
                news.getText()
        ));
    }

    /**
     * 删除某个
     * @param objects
     */
    @RabbitListener(queues = RabbitDeleteConfig.DEL_QUEUE_NAME)
    public void del(Object[] objects) {
        try {
            elasticsearchOperations.delete(objects[0].toString(), Class.forName(objects[1].toString()));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
