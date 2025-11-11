package com.book.store.mapper;

import com.book.store.entity.Book;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest
@MapperScan("com.book.store.mapper")
@ActiveProfiles("test") // 使用 application-test.yaml
public class BookMapperTest {

    @Autowired
    private BookMapper bookMapper;

    @Test
    void testInsertAndQuery() throws InterruptedException {
        Book book = new Book();
        book.setUuid(UUID.randomUUID().toString());
        book.setName("Test Book");
        book.setType("Fiction");
        book.setAuthor("Jiawen");
        book.setNationality("CN");
        book.setPress("Test Press");
        book.setStockQuantity(10);

        bookMapper.insert(book);

        List<Book> books = bookMapper.query();
        assertFalse(books.isEmpty());
        System.out.println("Books in H2: " + books);
    }
}
