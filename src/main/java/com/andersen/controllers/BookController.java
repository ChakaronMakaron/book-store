package com.andersen.controllers;

import com.andersen.models.Book;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.util.List;

public interface BookController {

    List<Book> list(HttpServletRequest request, HttpServletResponse response);
    void add(HttpServletRequest request, HttpServletResponse response);
}
