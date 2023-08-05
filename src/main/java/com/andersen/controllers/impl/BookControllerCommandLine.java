package com.andersen.controllers.impl;

import com.andersen.controllers.BookController;
import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;
import com.andersen.services.BookService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BookControllerCommandLine implements BookController {
    private final BookService bookService;

    public BookControllerCommandLine(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void list(String sortKey) {
        List<Book> books = bookService.getAll();

        try {
            BookSortKey bookSortKey = BookSortKey.valueOf(sortKey.toUpperCase());

            sort(books, bookSortKey);

            books.forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.out.printf("Wrong sort key (valid sorting keys - %s)",
                    Arrays.stream(BookSortKey.values())
                            .map(BookSortKey::toString)
                            .collect(Collectors.joining(", ", "", ".")));
        }
    }

    @Override
    public void add(String name, int amountToAdd) {

    }

    public static void sort(List<Book> books, BookSortKey bookSortKey) {
        switch (bookSortKey) {
            case NAME -> books.sort(Comparator.comparing(Book::getName));
            case PRICE -> books.sort(Comparator.comparing(Book::getPrice));
            case AMOUNT -> books.sort(Comparator.comparing(Book::getAmount));
        }
    }
}
