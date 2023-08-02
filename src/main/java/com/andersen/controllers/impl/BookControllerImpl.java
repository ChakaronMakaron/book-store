package com.andersen.controllers.impl;

import com.andersen.controllers.BookController;
import com.andersen.models.Book;
import com.andersen.services.BookService;

import java.util.Comparator;

import static java.lang.System.out;

public class BookControllerImpl implements BookController {
    private final BookService bookService;

    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void list(String sortKey) {
        switch (sortKey) {
            case "name":
                bookService.getAll().stream()
                        .sorted(Comparator.comparing(Book::getName))
                        .forEach(out::println);
                break;
            case "price":
                bookService.getAll().stream()
                        .sorted(Comparator.comparing(Book::getPrice))
                        .forEach(out::println);
                break;
            case "availability":
                bookService.getAll().stream()
                        .sorted(Comparator.comparing(Book::getAmount))
                        .forEach(out::println);
                break;
        }
    }

    @Override
    public void add(String name, int amountToAdd) {

    }
}
