package com.andersen.controllers.impl;

import java.util.Collections;
import java.util.List;

import com.andersen.annotations.Get;
import com.andersen.annotations.Post;
import com.andersen.controllers.BookController;
import com.andersen.models.Book;
import com.andersen.services.BookService;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Singleton
public class ServletBookController implements BookController {

    private final BookService bookService;

    @Inject
    public ServletBookController(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    @Get("/books/list")
    public List<Book> list(HttpServletRequest request, HttpServletResponse response) {
        /*
        BookSortKey bookSortKey = BookSortKey.valueOf(sortKey.toUpperCase());
        List<Book> books = bookService.list(bookSortKey);
        books.forEach(System.out::println);
        return books;
        */
        return Collections.emptyList();
    }

    @Override
    @Post("/books/add")
    public void add(HttpServletRequest request, HttpServletResponse response) {
        /*
        bookService.changeAmountOfBook(id, bookService.getBookById(id).
                orElseThrow(() -> new IllegalArgumentException("No book with this id")).getAmount() + amountToAdd);
        */
    }
}
