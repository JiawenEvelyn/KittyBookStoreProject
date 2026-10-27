package com.book.store.mapper;

import com.book.store.common.ReadingStatus;
import com.book.store.entity.UserBook;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserBookMapper {
    void insert(UserBook record);

    void modifyRecord(UserBook record);

    List<UserBook> queryByStatus(@Param("userId") String userId, @Param("status") ReadingStatus status);

    UserBook querySingleRecord(@Param("userId") String userId, @Param("bookId") String bookId);

    void clearRating(@Param("userId") String userId, @Param("bookId") String bookId);
}
