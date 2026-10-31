package com.book.store.service;

import com.book.store.common.ErrorCode;
import com.book.store.common.ReadingStatus;
import com.book.store.entity.Book;
import com.book.store.entity.UserBook;
import com.book.store.exception.BizException;
import com.book.store.mapper.BookMapper;
import com.book.store.mapper.UserBookMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static com.book.store.common.ReadingStatus.READ;
import static com.book.store.common.ReadingStatus.WANT;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@MapperScan("com.book.store.mapper")
@ActiveProfiles("test") // 使用 application-test.yaml
@Transactional
public class UserBookServiceTest {
    @Mock
    private BookMapper bookMapper;

    @Mock
    private UserBookMapper userBookMapper;

    @InjectMocks
    private UserBookService userBookService;

    private String userId = UUID.randomUUID().toString();

    private String bookId = UUID.randomUUID().toString();

    private UserBook userBook = initUserBook();

    private Book book = initBook();

    private UserBook initUserBook() {
        UserBook tmp = new UserBook();
        tmp.setBookId(bookId);
        tmp.setUserId(userId);
        tmp.setStatus(ReadingStatus.READING);
        tmp.setReadCount(0);
        return tmp;
    }

    private Book initBook() {
        Book tmp = new Book();
        tmp.setId(bookId);
        tmp.setAuthorId(UUID.randomUUID().toString());
        tmp.setName("1892");
        return tmp;
    }

    @Test
    void addRecordShouldReturnNotFoundWhenBookNotExisted() {
        when(bookMapper.queryById(bookId)).thenReturn(null);
        BizException e = assertThrows(BizException.class, () -> userBookService.addRecord(bookId, userId, ReadingStatus.WANT));
        assertThat(e.getErrorCode().equals(ErrorCode.USERBOOK_NOT_FOUND));
    }

    @Test
    void addRecordShouldSuccess() {
        when(bookMapper.queryById(bookId)).thenReturn(book);
        userBookService.addRecord(bookId, userId, ReadingStatus.READING);
        verify(userBookMapper).insert(any());
    }

    @Test
    void updateRecordShouldFailedBecauseOfNotExisted() {
        when(userBookMapper.querySingleRecord(userId, bookId)).thenReturn(null);
        BizException e = assertThrows(BizException.class, () -> userBookService.updateRecord(bookId, userId, READ, 8));
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.USERBOOK_NOT_FOUND);
    }

    @Test
    void updateRecordShouldFailedBecauseOfRequestInconsistent() {
        when(userBookMapper.querySingleRecord(userId, bookId)).thenReturn(userBook);
        BizException e = assertThrows(BizException.class, () -> userBookService.updateRecord(bookId, userId, WANT, 8));
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.RATING_NOT_ALLOWED);
    }

    @Test
    void updateRecordShouldSuccessWhenUpdateReadCount() {
        UserBook existed = new UserBook();
        existed.setBookId(bookId);
        existed.setUserId(userId);
        existed.setStatus(ReadingStatus.READ);
        existed.setReadCount(1);
        existed.setRating(8);
        existed.setFinishedAt(LocalDate.of(2025, 1, 1));

        when(userBookMapper.querySingleRecord(userId, bookId)).thenReturn(existed);
        userBookService.updateRecord(bookId, userId, READ, 9);

        UserBook newRecord = new UserBook();
        newRecord.setBookId(bookId);
        newRecord.setUserId(userId);
        newRecord.setStatus(ReadingStatus.READ);
        newRecord.setReadCount(2);
        newRecord.setRating(9);
        newRecord.setFinishedAt(LocalDate.of(2026, 1, 1));

    }

}
