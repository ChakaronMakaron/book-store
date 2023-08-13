package com.andersen.controllers.impl;

import com.andersen.controllers.BookController;
import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;
import com.andersen.services.BookService;
import com.google.inject.Inject;

import java.util.List;

public class BookControllerCommandLine implements BookController {

    @Inject
    private BookService bookService;

    @Override
    public List<Book> getAll() {
        return bookService.getAll();
    }

    @Override
    public List<Book> getAllSorted(String sortKey) {
        BookSortKey bookSortKey = BookSortKey.valueOf(sortKey.toUpperCase());
        return bookService.getAllSorted(bookSortKey);
    }

    @Override
    public void changeBookStatus(Long id, Book.BookStatus status) {
        bookService.changeBookStatus(id, status);
    }
}


