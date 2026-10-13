package com.book.store.dto;

import com.book.store.entity.Author;
import com.book.store.entity.Book;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class BookRequest {
    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Size(max = 32)
    private String category;

    @NotBlank
    @Size(max = 50)
    private String press;

    @Size(max = 2000)
    private String introduction;

    @NotNull
    @Valid
    private AuthorRequest author;

    public static Book toEntity(BookRequest bookRequest) {
        Book book = new Book();
        book.setName(bookRequest.getName());
        book.setCategory(bookRequest.getCategory());
        book.setPress(bookRequest.getPress());
        book.setIntroduction(bookRequest.getIntroduction());

        Author author = AuthorRequest.toEntity(bookRequest.getAuthor());
        book.setAuthor(author);
        return book;
    }

}
