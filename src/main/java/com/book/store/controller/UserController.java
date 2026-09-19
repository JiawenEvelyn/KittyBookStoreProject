package com.book.store.controller;

import com.book.store.common.Result;
import com.book.store.dto.LoginRequest;
import com.book.store.dto.RegisterRequest;
import com.book.store.service.UserService;
import com.book.store.entity.User;
import com.book.store.vo.LoginVO;
import com.book.store.vo.UserVO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    /*
    * 注册
    * */
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public Result<UserVO> register(@Valid @RequestBody RegisterRequest registerRequest) {
        User userDb = userService.register(RegisterRequest.toEntity(registerRequest));
        return Result.ok(UserVO.from(userDb));
    }

    /*
     * 登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginRequest loginRequest) {
        User userDb = userService.login(loginRequest.getName(), loginRequest.getPassword());
        return Result.ok(LoginVO.from(userDb));
    }

    /*
    * 查询用户
    * */
    @GetMapping("/{id}")
    public Result<UserVO> queryUser(@PathVariable String id) {
        User userDb = userService.queryUserById(id);
        return Result.ok(UserVO.from(userDb));
    }
}
