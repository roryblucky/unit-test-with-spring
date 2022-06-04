package com.unitests.spring.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
 * using PgSQL as a real database
 */
@Mapper
public interface AnythingMapper {
    @Insert("INSERT INTO ANYTHING(id, request_id, content) VALUES(#{id}, #{requestId}, #{content})")
    int insertNew(AnythingEntity anything);

    @Select("SELECT id, request_id, content from ANYTHING WHERE id = #{id}")
    AnythingEntity selectOne(String id);
}
