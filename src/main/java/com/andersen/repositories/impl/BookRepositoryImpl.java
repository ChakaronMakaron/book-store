package com.andersen.repositories.impl;

import com.andersen.models.Book;
import com.andersen.repositories.BookRepository;

import java.util.Arrays;
import java.util.List;

public class BookRepositoryImpl implements BookRepository {
    private static final List<Book> books = Arrays.asList(
            new Book(0L, "The lord of the rings", 50, 123),
            new Book(1L, "Don Quixote", 125, 10),
            new Book(2L, "The catcher in the rye", 23, 5),
            new Book(3L, "The hobbit", 250, 65)
    );
    @Override
    public List<Book> getAll() {
        return books;
    }
}
