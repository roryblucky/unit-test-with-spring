package com.unitests.spring.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Mybatis unit tests
 */
@MybatisTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AnythingMapperTest {

    @Autowired
    private AnythingMapper anythingMapper;

    @Test
    void testInsertNew() {

        int result = anythingMapper.insertNew(AnythingEntity
                .builder().id("id").content("content").requestId("requestid").build());
        assertTrue(result != 0);
    }

    @Test
    void testSelectOne() {
        AnythingEntity anythingEntity = anythingMapper.selectOne("123");
        assertEquals("requestIdTest", anythingEntity.getRequestId());
    }
}