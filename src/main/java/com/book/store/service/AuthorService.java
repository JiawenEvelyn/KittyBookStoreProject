package com.book.store.service;

import com.book.store.common.ErrorCode;
import com.book.store.entity.Author;
import com.book.store.exception.BizException;
import com.book.store.mapper.AuthorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

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
        authorMapper.insert(author);
        return author;
    }

    public Author findOrCreate(String name, String nationality, LocalDate birthday) {
        Author authorDb = authorMapper.queryByNameNationality(name, nationality);
        if (authorDb != null) {
            return authorDb;
        }
        Author authorNew = new Author(name, nationality, birthday);
        authorNew.setId(UUID.randomUUID().toString());
        authorMapper.insert(authorNew);
        return authorNew;
    }
}