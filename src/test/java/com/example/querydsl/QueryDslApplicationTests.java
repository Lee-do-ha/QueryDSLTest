package com.example.querydsl;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@Profile("test")
class QueryDslApplicationTests {

    @Test
    void member_join() {
    }

}
