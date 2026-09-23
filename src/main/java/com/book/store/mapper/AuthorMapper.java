package com.book.store.mapper;

import com.book.store.entity.Author;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface AuthorMapper {

    void insert(Author author);

    Author queryById(String id);

    Author queryByNameNationality(@Param("name") String name, @Param("nationality") String nationality);
}
