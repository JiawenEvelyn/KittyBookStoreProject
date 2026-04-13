package com.book.store.entity;

import lombok.Data;

@Data
public class User {
    private Long id;

    private String name;

    private String email;

    private String phone;

    private String password;

    private String nationality;

    private String createAt;
}
