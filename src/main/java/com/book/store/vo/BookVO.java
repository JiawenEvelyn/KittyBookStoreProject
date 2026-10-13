package com.book.store.vo;

import com.book.store.entity.Book;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Objects;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookVO {
    private String id;

    private String name;

    private String category;

    private String authorId;

    private String press;

    private String introduction;

    private AuthorVO author;

    public static BookVO from(Book book) {
        BookVO bookVO = new BookVO();
        bookVO.setId(book.getId());
        bookVO.setName(book.getName());
        bookVO.setCategory(book.getCategory());
        bookVO.setPress(book.getPress());
        bookVO.setAuthorId(book.getAuthorId());
        bookVO.setIntroduction(book.getIntroduction());
        bookVO.setAuthor(Objects.nonNull(book.getAuthor()) ? AuthorVO.from(book.getAuthor()) : null);
        return bookVO;
    }
}
