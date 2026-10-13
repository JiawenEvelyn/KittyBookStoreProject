package com.book.store.entity;

import lombok.Data;

import java.time.LocalDate;

@Data
public class Author {
    private String id;

    private String name;

    private String nationality;

    private LocalDate birthday;

    public Author() {}

    public Author(String name, String nationality) {
        this.setName(name);
        this.setNationality(nationality);
    }

    public Author(String name, String nationality, LocalDate birthday) {
        this.setName(name);
        this.setNationality(nationality);
        this.setBirthday(birthday);
    }
}
