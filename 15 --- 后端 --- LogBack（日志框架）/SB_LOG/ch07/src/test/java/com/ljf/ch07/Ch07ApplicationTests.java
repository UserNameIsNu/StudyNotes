package com.ljf.ch07;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.SQLException;

@SpringBootTest
@Slf4j
class Ch07ApplicationTests {
    @Autowired
    private DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        log.info("connection: {}", dataSource);
        log.info("connection: {}", dataSource.getConnection());
    }

}
