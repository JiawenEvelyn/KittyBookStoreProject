package com.book.store.mapper;

import com.book.store.common.ReadingStatus;
import com.book.store.entity.UserBook;
import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserBookMapper {
    void insert(UserBook record);

    void modifyStatusOrRating(UserBook record);

    UserBook queryByStatus(@Param("userId") String userId, @Param("status") Integer status);
}
