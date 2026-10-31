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
            log.warn("insert user book record failed! DuplicateKeyException occurred.");
            throw new BizException(ErrorCode.USERBOOK_EXISTED);
        }

        return userBook;
    }

    @Transactional
    public void updateRecord(String bookId, String userId, ReadingStatus status, Integer rating) {
        UserBook userBook = userBookMapper.querySingleRecord(userId, bookId);
        if (userBook == null) {
            throw new BizException(ErrorCode.USERBOOK_NOT_FOUND);
        }
        ReadingStatus oldStatus = userBook.getStatus();
        ReadingStatus newStatus = status != null ? status : oldStatus;

        if (Objects.nonNull(rating) && !newStatus.equals(ReadingStatus.READ)) {
            throw new BizException(ErrorCode.RATING_NOT_ALLOWED);
        }

        userBook.setStatus(newStatus);
        if (newStatus.equals(ReadingStatus.READ)) {
            if (oldStatus != ReadingStatus.READ) {
                userBook.setReadCount(userBook.getReadCount() + 1);
                userBook.setFinishedAt(LocalDate.now());
            }
            userBook.setRating(rating);
        }

        userBookMapper.modifyRecord(userBook);

        if (newStatus.equals(ReadingStatus.WANT)) {
            userBookMapper.clearRating(userId, bookId);
        }
    }
}
