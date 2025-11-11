package com.book.store.mapper;

import com.book.store.entity.Book;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BookMapper {

    List<Book> query();

    void insert(Book book);
}

