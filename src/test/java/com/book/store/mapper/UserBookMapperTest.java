package com.book.store.mapper;

import com.book.store.common.ReadingStatus;
import com.book.store.entity.UserBook;
import net.bytebuddy.asm.MemberSubstitution;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@MapperScan("com.book.store.mapper")
@ActiveProfiles("test") // 使用 application-test.yaml
@Transactional
public class UserBookMapperTest {

    @Autowired
    private UserBookMapper userBookMapper;

    private UserBook userBook = initData();

    private UserBook initData() {
        UserBook userBook = new UserBook();
        userBook.setBookId(UUID.randomUUID().toString());
        userBook.setUserId(UUID.randomUUID().toString());
        userBook.setRating(8);
        userBook.setFinishedAt(LocalDate.of(2000, 1, 1));
        userBook.setStatus(ReadingStatus.READ);
        userBook.setReadCount(1);
        return userBook;
    }

    @Test
    void testInsertAndQueryWithId() {
        userBookMapper.insert(userBook);

        UserBook result = userBookMapper.querySingleRecord(userBook.getUserId(), userBook.getBookId());
        assertNotNull(result);
        assertEquals(userBook.getRating(), result.getRating());
        assertEquals(userBook.getReadCount(), result.getReadCount());
        assertEquals(userBook.getStatus(), result.getStatus());
        assertNotNull(result.getCreatedAt());
        assertEquals(userBook.getFinishedAt(), result.getFinishedAt());
    }

    @Test
    void testModifyRecordShouldReturnCorrectValue() {
        userBookMapper.insert(userBook);

        UserBook modify = new UserBook();
        modify.setUserId(userBook.getUserId());
        modify.setBookId(userBook.getBookId());
        modify.setRating(5);

        userBookMapper.modifyRecord(modify);
        UserBook result = userBookMapper.querySingleRecord(userBook.getUserId(), userBook.getBookId());
        assertEquals(modify.getRating(), result.getRating());
        assertEquals(userBook.getStatus(), result.getStatus());
        assertEquals(userBook.getReadCount(), result.getReadCount());
    }

    @Test
    void testClearRatingShouldBeNull() {
        userBookMapper.insert(userBook);
        UserBook result = userBookMapper.querySingleRecord(userBook.getUserId(), userBook.getBookId());
        assertEquals(userBook.getRating(), result.getRating());

        userBookMapper.clearRating(userBook.getUserId(), userBook.getBookId());
        UserBook clearResult = userBookMapper.querySingleRecord(userBook.getUserId(), userBook.getBookId());
        assertNull(clearResult.getRating());
    }

    @Test
    void testQueryStausShouldReturnOneResult() {
        userBookMapper.insert(userBook);

        UserBook userBookReading = new UserBook();
        userBookReading.setBookId(UUID.randomUUID().toString());
        userBookReading.setUserId(userBook.getUserId());
        userBookReading.setRating(8);
        userBookReading.setFinishedAt(LocalDate.of(2000, 1, 1));
        userBookReading.setStatus(ReadingStatus.READING);
        userBookReading.setReadCount(1);

        userBookMapper.insert(userBookReading);

        List<UserBook> results = userBookMapper.queryByStatus(userBook.getUserId(), ReadingStatus.READING);
        assertEquals(1, results.size());
        assertEquals(ReadingStatus.READING, results.get(0).getStatus());
    }
}


