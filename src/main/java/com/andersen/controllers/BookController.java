package com.andersen.controllers;

import com.andersen.models.Book;

import java.util.List;

public interface BookController {

    List<Book> getAll();
    List<Book> getAllSorted(String sortKey);

    void changeBookStatus(Long id, Book.BookStatus status);
}
