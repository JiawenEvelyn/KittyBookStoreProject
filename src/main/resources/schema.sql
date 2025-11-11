CREATE TABLE IF NOT EXISTS tbl_book (
    uuid VARCHAR(36) PRIMARY KEY,
    name VARCHAR(36) NOT NULL,
    type VARCHAR(12) NOT NULL,
    author VARCHAR(20) NOT NULL,
    nationality VARCHAR(12) NOT NULL,
    press VARCHAR(50) NOT NULL,
    stock_quantity INTEGER NOT NULL DEFAULT 0,
    introduction VARCHAR(200)
);