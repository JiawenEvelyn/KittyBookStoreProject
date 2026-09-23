package com.book.store.service;

import com.book.store.common.ErrorCode;
import com.book.store.entity.Author;
import com.book.store.exception.BizException;
import com.book.store.mapper.AuthorMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorMapper authorMapper;

    @InjectMocks
    private AuthorService authorService;


    @Test
    void createAuthorShouldRejectDuplicateWithoutInserting() {
        Author stored = new Author("Kafka", "CZ", LocalDate.of(1883, 7, 3));
        stored.setId("stored-id");
        when(authorMapper.queryByNameNationality("Kafka", "CZ")).thenReturn(stored);

        Author incoming = new Author("Kafka", "CZ", null);
        BizException e = assertThrows(BizException.class, () -> authorService.createAuthor(incoming));

        assertThat(e.getErrorCode()).isEqualTo(ErrorCode.AUTHOR_EXISTED);
        verify(authorMapper, never()).insert(any());
    }


    @Test
    void createAuthorShouldInsertOnceWithoutDuplicate() {
        when(authorMapper.queryByNameNationality("Kafka", "CZ")).thenReturn(null);
        Author incoming = new Author("Kafka", "CZ", LocalDate.of(1883, 7, 3));

        Author result = authorService.createAuthor(incoming);
        assertThat(result.getName()).isEqualTo(incoming.getName());
        assertThat(result.getNationality()).isEqualTo(incoming.getNationality());
        assertNotNull(result.getId());
        verify(authorMapper).insert(incoming);
    }

    @Test
    void findOrCreateShouldReturnDBWithExistedRecord() {
        Author existed = new Author("Kafka", "CZ", LocalDate.of(1883, 7, 3));
        when(authorMapper.queryByNameNationality("Kafka", "CZ")).thenReturn(existed);

        Author result = authorService.findOrCreate("Kafka", "CZ", LocalDate.of(1883, 7, 3));
        assertThat(result.getName()).isEqualTo(existed.getName());
        assertThat(result.getNationality()).isEqualTo(existed.getNationality());
        verify(authorMapper, never()).insert(any());
    }

    @Test
    void findOrCreateShouldCreateWithoutExisted() {
        when(authorMapper.queryByNameNationality("Kafka", "CZ")).thenReturn(null);

        Author result = authorService.findOrCreate("Kafka", "CZ", LocalDate.of(1883, 7, 3));
        assertNotNull(result.getId());
        verify(authorMapper).insert(any());
    }
}
