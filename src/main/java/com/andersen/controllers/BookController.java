package com.andersen.controllers;

import com.andersen.models.Book;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface BookController {

    List<Book> getAll(HttpServletRequest request, HttpServletResponse response);
    List<Book> getAllSorted(String sortKey);

    void changeBookStatus(Long id, Book.BookStatus status);
}
