package com.book.store.mapper;

import com.book.store.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("""
            INSERT INTO tbl_user (id, name, email, phone, password, nationality)
             VALUES(#{id}, #{name}, #{email}, #{phone}, #{password}, #{nationality})
            """)
    void insert(User user);

    @Select("SELECT * FROM tbl_user WHERE name = #{name}")
    User queryByName(String name);

    @Select("SELECT * FROM tbl_user WHERE id = #{id}")
    User queryById(String id);
}
