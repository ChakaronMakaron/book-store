package com.andersen.controllers.impl;

import com.andersen.controllers.BookController;
import com.andersen.models.Book;
import com.andersen.services.BookService;

import java.util.Comparator;
import java.util.List;

public class BookControllerCommandLine implements BookController {
    private final BookService bookService;

    public BookControllerCommandLine(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void list(String sortKey) {
        List<Book> books = bookService.getAll();

        switch (sortKey) {
            case "name" -> books.sort(Comparator.comparing(Book::getName));
            case "price" -> books.sort(Comparator.comparing(Book::getPrice));
            case "availability" -> books.sort(Comparator.comparing(Book::getAmount));
        }

        books.forEach(System.out::println);
    }

    @Override
    public void add(String name, int amountToAdd) {

    }
}
