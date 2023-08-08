package com.andersen.controllers.impl;

import com.andersen.controllers.BookController;
import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;
import com.andersen.services.BookService;

import java.util.List;

public class BookControllerCommandLine implements BookController {

    private final BookService bookService;

    public BookControllerCommandLine(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public List<Book> list(String sortKey) {
        BookSortKey bookSortKey = BookSortKey.valueOf(sortKey.toUpperCase());
        List<Book> books = bookService.list(bookSortKey);
        books.forEach(System.out::println);
        return books;
    }

    @Override
    public void add(Long id, int amountToAdd) {
        bookService.changeAmountOfBook(id, bookService.getBookById(id).
                orElseThrow(() -> new IllegalArgumentException("No book with this id")).getAmount() + amountToAdd);
    }
}


