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
     * register user info
     */
    public User register(User user) {
        User exist = userMapper.queryByName(user.getName());
        if (exist != null) {
            throw new BizException(ErrorCode.USER_EXISTED);
        }
        user.setId(UUID.randomUUID().toString());
        userMapper.insert(user);
        return userMapper.queryById(user.getId());
    }

    /**
     * login service
     */
    public User login(String name, String password) {
        User userDb = userMapper.queryByName(name);
        if (userDb == null || !userDb.getPassword().equals(password)) {
            throw new BizException(ErrorCode.USER_UNAUTHORIZED);
        }
        return userDb;
    }

    /**
     * query user info
     */
    public User queryUserById(String id) {
        User userDb = userMapper.queryById(id);
        if (userDb == null) {
            throw new BizException(ErrorCode.USER_NOT_FOUND);
        }
        return userDb;
    }
}
