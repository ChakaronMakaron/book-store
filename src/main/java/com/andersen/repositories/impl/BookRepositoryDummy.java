package com.andersen.repositories.impl;

import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;
import com.andersen.repositories.BookRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BookRepositoryDummy implements BookRepository {

    private final List<Book> books;

    public BookRepositoryDummy(List<Book> books) {
        this.books = books;
    }


    @Override
    public List<Book> findAll() {
        return books;
    }

    @Override
    public Optional<Book> findByBookId(Long bookId) {
        if (bookId < 1L) {
            throw new IllegalArgumentException("Bad book id");
        }
        return books.stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst();
    }

    @Override
    public List<Book> list(BookSortKey sortKey) {
        List<Book> fetchedBooks = new ArrayList<>(books);
        if (sortKey != BookSortKey.NATURAL) {
            sort(fetchedBooks, sortKey);
        }
        return fetchedBooks;
    }

    public static void sort(List<Book> books, BookSortKey bookSortKey) {
        switch (bookSortKey) {
            case NAME -> books.sort(Comparator.comparing(Book::getName));
            case PRICE -> books.sort(Comparator.comparing(Book::getPrice));
            case AMOUNT -> books.sort(Comparator.comparing(Book::getAmount));
        }
    }
}
