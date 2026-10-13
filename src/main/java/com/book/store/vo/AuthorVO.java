package com.book.store.vo;

import com.book.store.entity.Author;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AuthorVO {
    private String id;

    private String name;

    private String nationality;

    private LocalDate birthday;

    public static AuthorVO from(Author author) {
        AuthorVO authorVO = new AuthorVO();
        authorVO.setId(author.getId());
        authorVO.setName(author.getName());
        authorVO.setNationality(author.getNationality());
        authorVO.setBirthday(author.getBirthday());
        return authorVO;
    }

}
