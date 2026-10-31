package com.book.store.service;

import com.book.store.common.ErrorCode;
import com.book.store.common.ReadingStatus;
import com.book.store.entity.Book;
import com.book.store.entity.UserBook;
import com.book.store.exception.BizException;
import com.book.store.mapper.BookMapper;
import com.book.store.mapper.UserBookMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Objects;

@Slf4j
@Service
public class UserBookService {

    @Autowired
    private UserBookMapper userBookMapper;

    @Autowired
    private BookMapper bookMapper;

    public UserBook addRecord(String bookId, String userId, ReadingStatus status) {
        Book book = bookMapper.queryById(bookId);
        if (book == null) {
            throw new BizException(ErrorCode.BOOK_NOT_FOUND);
        }

        UserBook userBook = new UserBook();
        userBook.setBookId(bookId);
        userBook.setUserId(userId);
        userBook.setStatus(status);
        if (status.equals(ReadingStatus.READ)) {
            userBook.setReadCount(1);
            userBook.setFinishedAt(LocalDate.now());
        } else {
            userBook.setReadCount(0);
        }
        try {
            userBookMapper.insert(userBook);
        } catch (DuplicateKeyException exception) {
            log.error("insert user book record failed! DuplicateKeyException occurred: ", exception);
            throw new BizException(ErrorCode.RECORD_EXISTED);
        }

        return userBook;
    }

    @Transactional
    public void updateRecord(String bookId, String userId, ReadingStatus status, Integer rating) {
        UserBook userBook = userBookMapper.querySingleRecord(bookId, userId);
        if (userBook == null) {
            throw new BizException(ErrorCode.RECORD_NOT_FOUND);
        }
        if (Objects.nonNull(rating) && !status.equals(ReadingStatus.READ)) {
            throw new BizException(ErrorCode.BAD_REQUEST);
        }
        
        userBook.setStatus(status);
        if (status.equals(ReadingStatus.READ)) {
            if (userBook.getStatus() != ReadingStatus.READ) {
                userBook.setReadCount(userBook.getReadCount() + 1);
            }
            userBook.setFinishedAt(LocalDate.now());
            userBook.setRating(rating);
        }

        if (status.equals(ReadingStatus.WANT)) {
            userBookMapper.clearRating(userId, bookId);
            return;
        }

        if (status.equals(ReadingStatus.READING) && userBook.getStatus().equals(ReadingStatus.READ)) {

        }

        userBookMapper.modifyRecord(userBook);
    }
}
