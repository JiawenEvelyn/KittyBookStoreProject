package com.book.store.dto;

import com.book.store.entity.Author;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class AuthorRequest {

    @NotBlank
    @Size(min = 2, max = 100)
    private String name;

    @NotBlank
    @Pattern(regexp = "^[A-Z]{2}$")
    private String nationality;

    private LocalDate birthday;

    public static Author toEntity(AuthorRequest authorRequest) {
        Author author = new Author();
        author.setName(authorRequest.getName());
        author.setNationality(authorRequest.getNationality());
        author.setBirthday(authorRequest.getBirthday());
        return author;
    }
}
