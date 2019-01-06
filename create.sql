CREATE TABLE author (
    id NUMBER NOT NULL AUTO_INCREMENT,
    name_author VARCHAR(100),
    happy_year NUMBER,
    sad_year NUMBER,
    happy_place VARCHAR(100),
    int_facts VARCHAR(200),
    alive NUMBER,
    PRIMARY KEY (id)
);

CREATE TABLE book (
    id NUMBER NOT NULL AUTO_INCREMENT,
    name_book VARCHAR(100),
    happy_year NUMBER,
    genre VARCHAR(100),
    pages NUMBER,
    PRIMARY KEY (id)
);

CREATE TABLE authors_for_book (
    author_id NUMBER NOT NULL,
    book_id NUMBER NOT NULL,
    FOREIGN KEY (author_id) REFERENCES author(id),
    FOREIGN KEY (book_id) REFERENCES book(id),
    PRIMARY KEY (author_id, book_id)
);
