package com.book.store.entity;

import lombok.Data;

/*
* @Data注解可以自动生成getter/setter/toString/equals/hashCode,
* 它适用于数据接口类
*   @Getter
*   @Setter
*   @ToString
*   @EqualsAndHashCode
*   @RequiredArgsConstruvtor
* */
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
