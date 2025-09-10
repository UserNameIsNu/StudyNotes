package edu.nf.ch01_homework;

import edu.nf.ch01_homework.mapper.CityMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Ch01HomeworkApplicationTests {

    private CityMapper mapper;

    @Autowired
    public Ch01HomeworkApplicationTests(CityMapper mapper) {
        this.mapper = mapper;
    }

    @Test
    void contextLoads() {
        mapper.selectForId("101010100");
    }

}
