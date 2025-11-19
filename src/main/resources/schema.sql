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
    nationality VARCHAR(12) NOT NULL,
    birthday VARCHAR(36) NOT NULL
    create_at VARCHAR(36) NOT NULL
);

CREATE TABLE IF NOT EXISTS tbl_booklist (
    id varchar(36) PRIMARY KEY,
    user_id varchar(36) NOT NULL,
    category varchar(12) NOT NULL,
    created_at VARCHAR(36) NOT NULL,
    description VARCHAR(200)
)

CREATE TABLE IF NOT EXISTS tbl_booklistdetail (
    list_id VARCHAR(36) PRIMARY KEY,
    book_id VARCHAR(36) PRIMARY KEY,
    book_name VARCHAR(36)
)

