package com.book.store.mapper;

import com.book.store.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface BookMapper {

    List<Book> query();

    void insert(Book book);

    Book queryById(String id);

    List<Book> queryByCategory(@Param("category") String category);

    Book queryByKey(@Param("name") String name, @Param("authorId")String authorId);
}

