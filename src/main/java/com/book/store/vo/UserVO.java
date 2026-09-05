package com.book.store.vo;

import com.book.store.entity.User;
import lombok.Data;

@Data
public class UserVO {
    private String id;

    private String name;

    private String email;

    private String nationality;

    public static UserVO from(User user) {
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setName(user.getName());
        userVO.setEmail(user.getEmail());
        userVO.setNationality(user.getNationality());
        return userVO;
    }
}
