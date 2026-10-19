package com.book.store.entity;

import com.book.store.common.ReadingStatus;
import lombok.Data;

@Data
public class UserBook {

    private String userId;

    private String bookId;

    private ReadingStatus status;

    private Integer readCount;

    private Integer rating;

    private String createdAt;

    private String finishedAt;
}
