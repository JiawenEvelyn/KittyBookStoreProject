package com.book.store.vo;

import com.book.store.entity.User;
import lombok.Data;

@Data
public class LoginVO {
    private UserVO user;

    public static LoginVO from(User user) {
        LoginVO loginVO = new LoginVO();
        loginVO.setUser(UserVO.from(user));
        return loginVO;
    }
}
