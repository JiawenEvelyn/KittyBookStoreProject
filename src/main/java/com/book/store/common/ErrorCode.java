package com.book.store.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/*
* 1. ErrorCode enum class is defined as an Object which
* includes code, message, and HTTP code.
* 2. So we need define these class members and the constructor
*/
@Getter
public enum ErrorCode {
    // 1. constant variables are written in the front of the body
    // 0 -- SUCCESS
    // 1xxx user
    // 2xxx book
    // 3xxx author
    // 9xxx general/system
    SUCCESS(0, "ok", HttpStatus.OK),

    USER_NOT_FOUND(1001, "User not found", HttpStatus.NOT_FOUND),
    USER_UNAUTHORIZED(1002, "Login failed", HttpStatus.UNAUTHORIZED),
    USER_EXISTED(1003, "User info conflict", HttpStatus.CONFLICT),

    BAD_REQUEST(9001, "Bad request", HttpStatus.BAD_REQUEST),
    BAD_MESSAGE(9002, "Invalid json format message", HttpStatus.BAD_REQUEST),
    UNEXPECTED(9999, "Internal service exception occurred.", HttpStatus.INTERNAL_SERVER_ERROR);

    // 2. Here are the members included in ErrorCode class
    private final Integer code;
    private final String message;
    private final HttpStatus httpStatus;

    // 3. The constructor called by the objects above(SUCCESS, USER_NOT_FOUND...)
    ErrorCode(Integer code, String message, HttpStatus httpStatus) {
        this.code = code;
        this.message = message;
        this.httpStatus = httpStatus;
    }
}


