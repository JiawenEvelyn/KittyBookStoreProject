package com.book.store.service;

import com.book.store.common.ErrorCode;
import com.book.store.entity.Author;
import com.book.store.entity.Book;
import com.book.store.exception.BizException;
import com.book.store.mapper.BookMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorService authorService;

    @Transactional
    public Book insertBookRecord(Book book) {
        Author author = authorService.findOrCreate(book.getAuthor());

        Book exist = bookMapper.queryByKey(book.getName(), author.getId());
        if (exist != null) {
            throw new BizException(ErrorCode.BOOK_EXISTED);
        }

        book.setId(UUID.randomUUID().toString());
        book.setAuthorId(author.getId());
        book.setAuthor(author);
        try {
            bookMapper.insert(book);
        } catch (DuplicateKeyException e) {
            log.warn("Duplicate key error occurred when inserting book: {}, author: {}", book.getName(), author.getName());
            throw new BizException(ErrorCode.BOOK_EXISTED);
        }

        return book;
    }

    public Book queryById(String id) {
        Book book = bookMapper.queryById(id);
        if (book == null) {
            throw new BizException(ErrorCode.BOOK_NOT_FOUND);
        }
        return book;
    }

    public List<Book> queryByCategory(String category) {
        if (category.isEmpty()) {
            log.warn("Bad parameter when query with category, which is empty!");
            throw new BizException(ErrorCode.BAD_REQUEST);
        }
        return bookMapper.queryByCategory(category);
    }
}
