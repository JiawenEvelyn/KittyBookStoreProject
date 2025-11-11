package com.book.store.entity;

import lombok.Data;

@Data
public class Book {
    private String uuid;

    private String name;

    private String type;

    private String author;

    private String nationality;

    private String press;

    private Integer stockQuantity;

    private String introduction;
}
