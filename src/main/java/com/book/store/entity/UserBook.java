package com.book.store.entity;

import com.book.store.common.ReadingStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserBook {

    private String userId;

    private String bookId;

    private ReadingStatus status;

    private Integer readCount;

    private Integer rating;

    private LocalDateTime createdAt;

    private LocalDateTime finishedAt;
}
