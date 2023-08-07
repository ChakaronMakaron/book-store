package com.andersen.controllers.impl;

import com.andersen.controllers.BookController;
import com.andersen.enums.BookSortKey;
import com.andersen.services.BookService;

public class BookControllerCommandLine implements BookController {

    private final BookService bookService;

    public BookControllerCommandLine(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void list(String sortKey) {
        BookSortKey bookSortKey = BookSortKey.valueOf(sortKey.toUpperCase());
        bookService.list(bookSortKey).forEach(System.out::println);
    }

    @Override
    public void add(Long id, int amountToAdd) {
        bookService.changeAmountOfBook(id, bookService.getBookById(id).
                orElseThrow(() -> new IllegalArgumentException("No book with this id")).getAmount() + amountToAdd);
    }
}


