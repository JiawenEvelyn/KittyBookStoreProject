package com.book.store.service;

import com.book.store.common.ErrorCode;
import com.book.store.entity.Author;
import com.book.store.entity.Book;
import com.book.store.exception.BizException;
import com.book.store.mapper.AuthorMapper;
import com.book.store.mapper.BookMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Mock
    private AuthorService authorService;

    @Test
    void insertDuplicateBookShouldReturn409Conflict() {
        Author author = new Author("Kafka", "CZ", LocalDate.of(1883, 8, 3));
        author.setId(UUID.randomUUID().toString());
        Book existed = new Book(UUID.randomUUID().toString(), "城堡", "Fiction", "上海译文", "", author);

        when(authorService.findOrCreate(author)).thenReturn(author);
        when(bookMapper.queryByKey(any(), any())).thenReturn(existed);

        BizException e = assertThrows(BizException.class, () -> bookService.insertBookRecord(existed));
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.BOOK_EXISTED);
        verify(bookMapper, never()).insert(any());
    }

    @Test
    void insertBookShouldReturnConflictWhenAnotherTaskInsertSuccess() {
        Author author = new Author("Kafka", "CZ", LocalDate.of(1883, 8, 3));
        author.setId(UUID.randomUUID().toString());
        Book existed = new Book(UUID.randomUUID().toString(), "城堡", "Fiction", "上海译文", "", author);

        when(authorService.findOrCreate(author)).thenReturn(author);
        when(bookMapper.queryByKey(any(), any())).thenReturn(null);
        doThrow(new DuplicateKeyException("Book inserted with duplicate key!")).when(bookMapper).insert(existed);

        BizException e = assertThrows(BizException.class, () -> bookService.insertBookRecord(existed));
        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.BOOK_EXISTED);
    }

    @Test
    void insertBookShouldSetAuthroIdWhenNormallyInsert() {
        Author author = new Author("Kafka", "CZ", LocalDate.of(1883, 8, 3));
        author.setId(UUID.randomUUID().toString());
        Book input = new Book();
        input.setName("城堡");
        input.setCategory("Fiction");
        input.setPress("上海译文");

        when(authorService.findOrCreate(any())).thenReturn(author);
        when(bookMapper.queryByKey(any(), any())).thenReturn(null);

        Book output = bookService.insertBookRecord(input);
        assertNotNull(output.getId());
        assertNotNull(output.getAuthorId());
        assertNotNull(output.getAuthor());
    }
}
