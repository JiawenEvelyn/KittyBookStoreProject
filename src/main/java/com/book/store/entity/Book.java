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
}
