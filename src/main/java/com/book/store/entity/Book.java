package com.book.store.entity;

import lombok.Data;

@Data
public class Book {
    private String id;

    private String name;

    private String category;

    private String authorId;

    private String press;

    private String introduction;

    private Author author;

    public Book() {}

    public Book(String id, String name, String category, String press, String introduction, Author author) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.press = press;
        this.introduction = introduction;
        this.author = author;
        this.authorId = author.getId();
    }
}
