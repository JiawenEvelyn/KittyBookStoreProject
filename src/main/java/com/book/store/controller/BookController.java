package com.book.store.controller;

import com.book.store.common.Result;
import com.book.store.dto.BookRequest;
import com.book.store.entity.Book;
import com.book.store.service.BookService;
import com.book.store.vo.BookVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {

    @Autowired
    BookService bookService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Result<BookVO> insertBookRecord(@Valid @RequestBody BookRequest bookRequest) {
        Book bookDb = bookService.insertBookRecord(BookRequest.toEntity(bookRequest));
        return Result.ok(BookVO.from(bookDb));
    }

    @GetMapping("/{id}")
    public Result<BookVO> queryBookById(@PathVariable String id) {
        Book bookDb = bookService.queryById(id);
        return Result.ok(BookVO.from(bookDb));
    }

    @GetMapping
    public Result<List<BookVO>> queryByCategory(@RequestParam String category) {
        List<Book> bookList = bookService.queryByCategory(category);
        return Result.ok(bookList.stream().map(BookVO::from).collect(Collectors.toList()));
    }

}
