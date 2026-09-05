package com.book.store.service;

import com.book.store.common.ErrorCode;
import com.book.store.entity.User;
import com.book.store.exception.BizException;
import com.book.store.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册用户实现方法
     */
    public boolean register(User user) {
        User exist = userMapper.queryByName(user.getName());
        if (exist != null) {
            return false;
        }
        user.setId(UUID.randomUUID().toString());
        userMapper.insert(user);
        return true;
    }

    /**
     * 登录用户
     */
    public User login(String name, String password) {
        User user = userMapper.queryByName(name);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }

    /**
     * 查询用户
     */
    public User queryUserById(String id) {
        User userDb = userMapper.queryById(id);
        if (userDb == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        return userDb;
    }
}
