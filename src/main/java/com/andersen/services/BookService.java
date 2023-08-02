package com.andersen.services;

import com.andersen.models.Book;
import com.andersen.repositories.BookRepository;

import java.util.List;

public interface BookService {
    public List<Book> getAll();
    public Book getBookById(Long bookId);
    public void changeAmountOfBook(Long bookId, Integer amount);
}
