package com.book.store.common;

import lombok.Getter;

@Getter
public enum ReadingStatus {
    WANT(0),
    READING(1),
    READ(2);

    private final Integer status;

    ReadingStatus(Integer status) {
        this.status = status;
    }

}
