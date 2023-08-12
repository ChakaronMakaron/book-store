package com.andersen.controllers.impl;

import java.util.List;

import com.andersen.annotations.Get;
import com.andersen.annotations.Post;
import com.andersen.controllers.BookController;
import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;
import com.andersen.services.BookService;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class ServletBookController implements BookController {

    private final BookService bookService;

    @Inject
    public ServletBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    @Get("/books/list")
    public List<Book> list(String sortKey) {
        BookSortKey bookSortKey = BookSortKey.valueOf(sortKey.toUpperCase());
        List<Book> books = bookService.list(bookSortKey);
        books.forEach(System.out::println);
        return books;
    }

    @Override
    @Post("/books/add")
    public void add(Long id, int amountToAdd) {
        bookService.changeAmountOfBook(id, bookService.getBookById(id).
                orElseThrow(() -> new IllegalArgumentException("No book with this id")).getAmount() + amountToAdd);
    }
}
