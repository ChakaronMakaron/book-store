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
    public List<Book> getAll() {
        return books;
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        if (bookId < 1L) {
            throw new IllegalArgumentException("Bad book id");
        }
        return books.stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst();
    }

    @Override
    public List<Book> getAllSorted(BookSortKey sortKey) {
        List<Book> fetchedBooks = new ArrayList<>(books);
        if (sortKey != BookSortKey.NATURAL) {
            sort(fetchedBooks, sortKey);
        }
        return fetchedBooks;
    }

    @Override
    public void supply(Long id, int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount value is not valid");
        }
        findById(id).ifPresent(book -> book.setAmount(book.getAmount() + amount));
    }

    @Override
    public void changeBookStatus(Long id, Book.BookStatus status) {
        findById(id).orElseThrow(() -> new IllegalArgumentException("Wrong book id")).setStatus(status);
    }

    private void sort(List<Book> books, BookSortKey bookSortKey) {
        switch (bookSortKey) {
            case NAME -> books.sort(Comparator.comparing(Book::getName));
            case PRICE -> books.sort(Comparator.comparing(Book::getPrice));
            case AMOUNT -> books.sort(Comparator.comparing(Book::getAmount));
            default -> throw new IllegalArgumentException("Unexpected value: " + bookSortKey);
        }
    }
}
