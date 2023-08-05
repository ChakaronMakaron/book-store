package com.andersen.services;

import java.util.List;
import java.util.Optional;

import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;

public interface BookService {
    
    List<Book> list(BookSortKey sortKey);
    List<Book> getAll();
    Optional<Book> getBookById(Long bookId);
    void changeAmountOfBook(Long bookId, Integer amount);
}
