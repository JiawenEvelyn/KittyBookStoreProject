CREATE TABLE IF NOT EXISTS tbl_book (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(36) NOT NULL,
    category VARCHAR(12) NOT NULL,
    author_id VARCHAR(36) NOT NULL,
    press VARCHAR(50) NOT NULL,
    stock_quantity INTEGER NOT NULL DEFAULT 0,
    introduction VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS tbl_author (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(36) NOT NULL,
    nationality VARCHAR(12) NOT NULL,
    birthday VARCHAR(36) NOT NULL
);

CREATE TABLE IF NOT EXISTS tbl_user (
    id VARCHAR(36) PRIMARY KEY,
    name VARCHAR(36) NOT NULL,
    email VARCHAR(36) NOT NULL,
    phone VARCHAR(36) NOT NULL,
    password VARCHAR(12) NOT NULL,
    nationality VARCHAR(12) NOT NULL,
    birthday VARCHAR(36) NOT NULL,
    create_at VARCHAR(36) NOT NULL
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