package com.example.ch11_homework.service.impl;

import com.example.ch11_homework.entity.City;
import com.example.ch11_homework.mapper.CityMapper;
import com.example.ch11_homework.service.CityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class CityServiceImpl implements CityService {
    private final CityMapper cityMapper;
    private StringRedisTemplate stringRedisTemplate;
    private ObjectMapper objectMapper;

    public CityServiceImpl(CityMapper cityMapper, StringRedisTemplate stringRedisTemplate, ObjectMapper objectMapper) {
        this.cityMapper = cityMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.objectMapper = objectMapper;
    }

    /**
     * 整个查询结果为一个暂存
     * 如查询前五个，前五个就被暂存
     * 理应查询前三个，包含在前五个中，但会被重新暂存一个前三个的
     * @param pageNum
     * @param pageSize
     * @return
     */
    // 将每次查询的整个结果独立暂存
    @Override
    public List<City> listCity(int pageNum, int pageSize) {
        // 保存key
        String cacheKey = "city:list:" + pageNum + ":" + pageSize;
        // 先查缓存
        try {
            // 有没有从缓存里找到
            if (stringRedisTemplate.hasKey(cacheKey)) {
                // 找到了，返回
                log.info("缓存里找到了");
                return objectMapper.readValue(
                        stringRedisTemplate.opsForValue().get(cacheKey),
                        objectMapper.getTypeFactory().constructCollectionType(List.class, City.class));
            }
            // 缓存里没有，查数据库
            log.info("缓存里没有，去数据库拿一份");
            List<City> list = cityMapper.listCity(pageNum, pageSize);
            // 丢进缓存
            stringRedisTemplate.opsForValue().set(cacheKey, objectMapper.writeValueAsString(list), Duration.ofMinutes(30));
            // 返回
            return list;
        } catch (JsonProcessingException e) {
            throw new RuntimeException("some thing is wrong：", e);
        }
    }

    /**
     * 试图解决上面的问题
     * 但页码特别大时区间前数据也会特别多
     * 可有会存到炸
     * @param pageNum
     * @param pageSize
     * @return
     */
    // 将目标区间与其前面所有数据一并缓存，且将对象集合拆分为对象分别缓存
    @Override
    public List<City> listCityPro(int pageNum, int pageSize) {
        try {
            // 已缓存的总条数
            Long size = stringRedisTemplate.opsForList().size("cityList");
            // 是否包含
            if (size.intValue() > pageNum * pageSize) {
                // 已包含
                // 返回
                return transform(pageNum, pageSize);
            } else {
                // 未包含
                // 清空
                stringRedisTemplate.delete("cityList");
                // 获取从第一个到目标的最后一个
                List<City> list = cityMapper.listCity(1, pageNum * pageSize);
                // 拆包逐个装填
                for (City city : list) {
                    stringRedisTemplate.opsForList().rightPush("cityList", objectMapper.writeValueAsString(city));
                }
                // 返回
                return transform(pageNum, pageSize);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("some thing is wrong：", e);
        }
    }

    private List<City> transform(int pageNum, int pageSize) {
        // 剪裁集合（目标区间），下标0起故减一
        // start：（当前页数-1）* 每页总数
        // end：当前页数 * 每页总数 - 1
        // 如：1页5数 ———— start：(1-1)*5=0，end：1*5-1=4 ———— 0-4
        // 如：2页5数 ———— start：(2-1)*5=5，end：2*5-1=9 ———— 5-9
        List<String> cachedJson = stringRedisTemplate.opsForList().range("cityList", (long) (pageNum - 1) * pageSize, (long) pageNum * pageSize - 1);
        // 反序列化回对象并返回
        return cachedJson.stream()
                .map(json -> {
                    try {
                        return objectMapper.readValue(json, City.class);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    /**
     * 试图解决上面的问题
     * 第二次的区间包含前一个区间的任意值时，缓存会失效
     * 无法正常缓存，且无法返回正确的目标区间
     * @param pageNum
     * @param pageSize
     * @return
     */
    // 仅暂存目标区间
    @Override
    public List<City> listCityProMax(int pageNum, int pageSize){
        try {
            // 是否包含目标区间
            if (stringRedisTemplate.opsForZSet().count("citySet", (pageNum - 1) * pageSize, pageNum * pageSize - 1) > 0) {
                // 已包含
                // 返回
                return transformPro(pageNum, pageSize);
            } else {
                // 未包含
                // 获取从第一个到目标的最后一个
                List<City> list = cityMapper.listCity(pageNum, pageSize);
                // 目标区间起始分数（第几个）
                int start = pageNum * pageSize - pageSize;
                // 拆包逐个装填
                for (City city : list) {
                    stringRedisTemplate.opsForZSet().add("citySet", objectMapper.writeValueAsString(city), start);
                    // 分数自增
                    start++;
                }
                // 返回
                return transformPro(pageNum, pageSize);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("some thing is wrong：", e);
        }
    }

    private List<City> transformPro(int pageNum, int pageSize) {
        // 获取区间
        Set<String> rangeByScore = stringRedisTemplate.opsForZSet().rangeByScore("citySet", (pageNum - 1) * pageSize, pageNum * pageSize - 1);
        // 反序列化回对象并返回
        return rangeByScore.stream()
                .map(json -> {
                    try {
                        return objectMapper.readValue(json, City.class);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();
    }

    /**
     * 试图解决上面的问题
     * @param pageNum
     * @param pageSize
     * @return
     */
    // 修改包含验证逻辑
    @Override
    public List<City> listCityProMaxUltra(int pageNum, int pageSize){
        try {
            Long count = stringRedisTemplate.opsForZSet().count("citySet", (pageNum - 1) * pageSize, pageNum * pageSize - 1);
            log.info("缓存区间：{},页数：{}，每页数：{}", count, pageNum, pageSize);
            // 是否包含目标区间（缓存是否存在目标区间，且缓存存在的目标区间（id排号）是否全匹配等于实际目标区间）
            if (count > 0 && count == pageSize) {
                // 已包含
                log.info("缓存包含");
                // 返回
                return transformPro(pageNum, pageSize);
            } else {
                // 未包含
                log.info("缓存未包含");
                // 获取从第一个到目标的最后一个
                List<City> list = cityMapper.listCity(pageNum, pageSize);
                // 目标区间起始分数（第几个）
                int start = pageNum * pageSize - pageSize;
                // 拆包逐个装填
                for (City city : list) {
                    stringRedisTemplate.opsForZSet().add("citySet", objectMapper.writeValueAsString(city), start);
                    // 分数自增
                    start++;
                }
                // 返回
                return transformPro(pageNum, pageSize);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException("some thing is wrong：", e);
        }
    }
}
