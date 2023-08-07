package com.andersen.repositories;

import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;

import java.util.List;
import java.util.Optional;


public interface BookRepository {
    List<Book> findAll();

    Optional<Book> findByBookId(Long bookId);

    List<Book> list(BookSortKey sortKey);

}
