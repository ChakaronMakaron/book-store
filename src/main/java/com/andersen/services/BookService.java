package com.andersen.services;

import java.util.List;

import com.andersen.models.Book;

public interface BookService {
    List<Book> getAll();
    Book getBookById(Long bookId);
    void changeAmountOfBook(Long bookId, Integer amount);
    void changeBookStatus(Long bookId, boolean bookStatus);
}
