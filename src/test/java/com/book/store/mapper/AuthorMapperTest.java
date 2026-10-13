package com.book.store.mapper;

import com.book.store.entity.Author;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@MapperScan("com.book.store.mapper")
@ActiveProfiles("test")
@Transactional
public class AuthorMapperTest {

    @Autowired
    private AuthorMapper authorMapper;

    @Test
    void insertAndQuery() {
        Author author = new Author("陀思妥耶夫斯基", "RN", LocalDate.of(1900, 1, 10));
        author.setId(UUID.randomUUID().toString());
        authorMapper.insert(author);

        Author authorDb = authorMapper.queryById(author.getId());
        assertThat(authorDb.getBirthday()).isEqualTo(LocalDate.of(1900, 1, 10));
        assertThat(authorDb.getId()).isEqualTo(author.getId());
    }

    @Test
    void queryByIdShouldReturnNull() {
        Author authorDb = authorMapper.queryById(UUID.randomUUID().toString());
        assertNull(authorDb);
    }


    @Test
    void queryByNameAndNationalityShouldReturnNull() {
        Author author = new Author("陀思妥耶夫斯基", "RN", LocalDate.of(1900, 1, 10));
        author.setId(UUID.randomUUID().toString());
        authorMapper.insert(author);
        Author authorDb = authorMapper.queryByNameNationality("陀思妥耶夫斯基", "CN");
        assertNull(authorDb);
    }

    @Test
    void queryByNameAndNationalityShouldReturnWithData() {
        Author author = new Author("陀思妥耶夫斯基", "RN", LocalDate.of(1900, 1, 10));
        author.setId(UUID.randomUUID().toString());
        authorMapper.insert(author);
        Author authorDb = authorMapper.queryByNameNationality("陀思妥耶夫斯基", "RN");
        assertNotNull(authorDb);
        assertThat(authorDb.getId()).isEqualTo(author.getId());
    }
}
