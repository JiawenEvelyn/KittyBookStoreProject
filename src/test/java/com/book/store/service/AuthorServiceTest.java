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
import org.springframework.dao.DuplicateKeyException;

import java.time.LocalDate;
import java.util.UUID;

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
        Author author = new Author("Kafka", "CZ", LocalDate.of(1883, 7, 3));
        when(authorMapper.queryByNameNationality("Kafka", "CZ")).thenReturn(author);

        Author result = authorService.findOrCreate(author);
        assertThat(result.getName()).isEqualTo(author.getName());
        assertThat(result.getNationality()).isEqualTo(author.getNationality());
        verify(authorMapper, never()).insert(any());
    }

    @Test
    void findOrCreateShouldCreateWithoutExisted() {
        when(authorMapper.queryByNameNationality("Kafka", "CZ")).thenReturn(null);
        Author author = new Author("Kafka", "CZ", LocalDate.of(1883, 7, 3));
        Author result = authorService.findOrCreate(author);
        assertNotNull(result.getId());
        verify(authorMapper).insert(any());
    }

    @Test
    void findOrCreateShouldReturnAuthorWhenInsertWithDuplicateKeyEx() {
        when(authorMapper.queryByNameNationality("kafka", "CZ")).thenReturn(null);
        doThrow(new DuplicateKeyException("duplicate key!")).when(authorMapper).insert(any());
        Author inserted = new Author("kafka", "CZ", LocalDate.of(1883, 7, 3));
        inserted.setId(UUID.randomUUID().toString());

        when(authorMapper.queryByNameNationalityForUpdate("kafka", "CZ")).thenReturn(inserted);
        Author author = authorService.findOrCreate(inserted);

        verify(authorMapper).queryByNameNationalityForUpdate("kafka", "CZ");
        assertNotNull(author);
        assertThat(author.getName()).isEqualTo("kafka");
        assertThat(author.getNationality()).isEqualTo("CZ");
    }
}
