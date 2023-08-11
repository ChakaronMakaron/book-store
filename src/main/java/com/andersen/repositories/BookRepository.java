package com.andersen.repositories;

import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;

import java.util.List;
import java.util.Optional;


public interface BookRepository {
    List<Book> getAll();

    Optional<Book> findById(Long bookId);

    List<Book> getAllSorted(BookSortKey sortKey);

    void supply(Long id, int amount);

    void changeBookStatus(Long id, Book.BookStatus status);
}
