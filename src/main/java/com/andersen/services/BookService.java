package com.andersen.services;

import com.andersen.models.Book;
import com.andersen.repositories.BookRepository;

import java.util.List;

public interface BookService {
    List<Book> getAll();
    Book getBookById(Long bookId);
    void changeAmountOfBook(Long bookId, Integer amount);
}
