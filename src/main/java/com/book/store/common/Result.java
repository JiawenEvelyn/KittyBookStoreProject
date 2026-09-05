package com.book.store.common;

import lombok.Getter;

/*
* 1. Result类声明的<T>属于实例，是在new Result<UserVO> ()才决定的
* 2. 这个类用于API向外部返回的通用响应体，包括响应码、响应信息和数据体
* 3. data作为泛型类型，可以适配任何类型的数据结构，因此不需要基于每一个
* 数据模型单独设计一个外部响应体
* */
@Getter
public class Result <T> {
    private Integer code;

    private String message;

    private T data;

    /*
    * 0. ok方法只处理成功场景的相应，因此入参只有一个，即具体数据;code和message
    * 直接在方法中写死
    * 1. 选择static的原因是，凡是创建对象的方法，都应该是static
    * 2. 静态方法上的<T>声明是属于“这次方法调用”；
    * 由于方法不属于任何对象，因此它无法使用Result类上的<T> -- 这个是在new具体对象时才确认的
    * 3. 这是一个静态工厂方法，就是用静态方法来代替new创建对象
    * */
    public static <T> Result<T> ok(T data) {
        Result<T> result = new Result<>();
        result.code = ErrorCode.SUCCESS.getCode();
        result.message = ErrorCode.SUCCESS.getMessage();
        result.data = data;
        return result;
    }

    public static <T> Result<T> fail(ErrorCode errorCode) {
        Result<T> result = new Result<>();
        result.code = errorCode.getCode();
        result.message = errorCode.getMessage();
        result.data = null;
        return result;
    }
}
