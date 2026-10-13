package com.book.store.service;

import com.book.store.common.ErrorCode;
import com.book.store.entity.Author;
import com.book.store.exception.BizException;
import com.book.store.mapper.AuthorMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Slf4j
@Service
public class AuthorService {

    @Autowired
    private AuthorMapper authorMapper;

    /*
    * create author information
    *
    */
    public Author createAuthor(Author author) {
        Author exist = authorMapper.queryByNameNationality(author.getName(), author.getNationality());
        if (exist != null) {
            throw new BizException(ErrorCode.AUTHOR_EXISTED);
        }
        author.setId(UUID.randomUUID().toString());
        try {
            authorMapper.insert(author);
        } catch (DuplicateKeyException e) {
            throw new BizException(ErrorCode.AUTHOR_EXISTED);
        }
        return author;
    }

    public Author findOrCreate(Author author) {
        Author authorDb = authorMapper.queryByNameNationality(author.getName(), author.getNationality());
        if (authorDb != null) {
            return authorDb;
        }

        author.setId(UUID.randomUUID().toString());
        try {
            authorMapper.insert(author);
        } catch (DuplicateKeyException e) {
            log.warn("Duplicate key error occurred when inserting author: {}, nationality: {}", author.getName(), author.getNationality());
            return authorMapper.queryByNameNationalityForUpdate(author.getName(), author.getNationality());
        }
        return author;
    }
}