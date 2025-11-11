//package com.book.store;
//
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DatabaseInitializer implements CommandLineRunner {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    public DatabaseInitializer(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        // 建表 SQL
//        String createBookDBSql = """
//            CREATE TABLE IF NOT EXISTS tbl_book (
//                uuid BINARY(16) PRIMARY KEY,
//                id INT AUTO_INCREMENT PRIMARY KEY,
//                name VARCHAR(50) NOT NULL,
//                age INT
//            );
//        """;
//        jdbcTemplate.execute(createBookDBSql);
//        System.out.println("✅ 数据表 user 已创建或已存在");
//    }
//}