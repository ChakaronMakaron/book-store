package com.andersen.repositories;

import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class BookRepository {

    private final List<Book> books;

    public BookRepository(List<Book> books) {
        this.books = books;
    }


    public List<Book> findAll() {
        return books;
    }

    public Optional<Book> findByBookId(Long bookId) {
        if (bookId < 1L) {
            throw new IllegalArgumentException("Bad book id");
        }
        return books.stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst();
    }

    public List<Book> list(BookSortKey sortKey) {
        List<Book> fetchedBooks = new ArrayList<>(books);
        sort(fetchedBooks, sortKey);
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
