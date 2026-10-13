package com.book.store.controller;

import com.book.store.common.Result;
import com.book.store.dto.AuthorRequest;
import com.book.store.entity.Author;
import com.book.store.service.AuthorService;
import com.book.store.vo.AuthorVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/authors")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Result<AuthorVO> createAuthor(@Valid @RequestBody AuthorRequest authorRequest) {
        Author authorDb = authorService.createAuthor(AuthorRequest.toEntity(authorRequest));
        return Result.ok(AuthorVO.from(authorDb));
    }
}