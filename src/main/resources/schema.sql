CREATE TABLE IF NOT EXISTS tbl_author (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    nationality CHAR(2) NOT NULL,
    birthday DATE NULL,
    UNIQUE(name, nationality)
);

CREATE TABLE IF NOT EXISTS tbl_book (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    category VARCHAR(32) NOT NULL,
    author_id VARCHAR(36) NOT NULL,
    press VARCHAR(50) NOT NULL,
    introduction VARCHAR(2000),
    CONSTRAINT fk_book_author FOREIGN KEY (author_id) REFERENCES tbl_author(id)
        ON DELETE RESTRICT ON UPDATE RESTRICT
);

CREATE TABLE IF NOT EXISTS tbl_user (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(36) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    password VARCHAR(100) NOT NULL,
    nationality VARCHAR(48) NOT NULL,
    create_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS tbl_rel_userbook (
    user_id VARCHAR(36) NOT NULL,
    book_id VARCHAR(36) NOT NULL,
    status VARCHAR(12) NOT NULL,
    rating integer NOT NULL,
    created_at VARCHAR(36) NOT NULL,
    PRIMARY KEY (user_id, book_id)
);

CREATE TABLE IF NOT EXISTS tbl_bookexcerpt (
    id VARCHAR(36) NOT NULL,
    book_id VARCHAR(36) NOT NULL,
    excerpt VARCHAR(2048) NOT NULL,
    comment VARCHAR(200) NOT NULL,
    created_at VARCHAR(36) NOT NULL,
    PRIMARY KEY (id, book_id)
);

CREATE TABLE IF NOT EXISTS tbl_manager (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(36) NOT NULL,
    password VARCHAR(12) NOT NULL,
    created_at VARCHAR(36) NOT NULL
);