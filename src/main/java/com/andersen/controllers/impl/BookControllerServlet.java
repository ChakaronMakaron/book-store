package com.andersen.controllers.impl;

import com.andersen.annotations.Get;
import com.andersen.controllers.BookController;
import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;
import com.andersen.services.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@Singleton
public class BookControllerServlet implements BookController {

    private final BookService bookService;

    @Inject
    public BookControllerServlet(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    @Get("/books/list")
    public List<Book> getAll(HttpServletRequest request, HttpServletResponse response) {
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


