package com.andersen.controllers.impl;

import com.andersen.annotations.Get;
import com.andersen.annotations.Put;
import com.andersen.controllers.BookController;
import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;
import com.andersen.services.BookService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

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
    @Get("/books/sorted")
    public List<Book> getAllSorted(HttpServletRequest request, HttpServletResponse response) {
        String sortKey = request.getParameter("sort");

        BookSortKey bookSortKey = BookSortKey.valueOf(sortKey.toUpperCase());
        return bookService.getAllSorted(bookSortKey);
    }

    @Override
    @Put("/books/change-status")
    public void changeBookStatus(HttpServletRequest request, HttpServletResponse response) {
        Long id = Long.valueOf(request.getParameter("id"));
        Book.BookStatus status = Book.BookStatus.valueOf(request.getParameter("status"));

        bookService.changeBookStatus(id, status);
    }
}


