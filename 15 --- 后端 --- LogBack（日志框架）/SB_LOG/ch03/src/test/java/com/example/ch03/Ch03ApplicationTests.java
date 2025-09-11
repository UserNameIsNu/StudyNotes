package com.example.ch03;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SpringBootTest
@Slf4j
class Ch03ApplicationTests {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testHikari() throws SQLException {
        log.info(dataSource.toString());
        log.info("connection: {}", dataSource.getConnection());
    }

    @Test
    void testTemplate() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from city_info");
        for (Map<String, Object> map : list) {
            log.info(map.toString());
        }
    }

}
