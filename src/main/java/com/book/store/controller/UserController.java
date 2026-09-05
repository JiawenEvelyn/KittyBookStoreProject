package com.book.store.controller;

import com.book.store.common.Result;
import com.book.store.service.UserService;
import com.book.store.entity.User;
import com.book.store.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /*
    * 注册
    * */
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        boolean success = userService.register(user);
        return success ? "注册成功" : "用户名已存在";
    }

    /*
     * 登录
     */
    @PostMapping("/login")
    public String login(@RequestParam String name, @RequestParam String password) {
        User user = userService.login(name, password);
        return user != null ? "登录成功" : "用户名或密码错误";
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
