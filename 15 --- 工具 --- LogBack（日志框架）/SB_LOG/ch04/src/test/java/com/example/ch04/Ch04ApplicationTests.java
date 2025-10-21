package com.example.ch04;

import com.example.ch04.entity.City;
import com.example.ch04.mapper.CityMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
@Slf4j
class Ch04ApplicationTests {
    @Autowired
    private CityMapper cityMapper;

    @Test
    void testListCity() {
        List<City> cityList = cityMapper.listCity(2, 10);
        cityList.forEach(city -> log.info("{}", city.getCityName()));
    }

}
