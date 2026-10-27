package com.book.store.common;

import lombok.Getter;

@Getter
public enum ReadingStatus {
    WANT,
    READING,
    READ;

    ReadingStatus() {}

}
