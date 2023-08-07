package com.andersen.repositories;

import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;

import java.util.*;

public class BookRepository {


//    private final List<Book> books = Arrays.asList(
//            new Book(1L,"The Great Gatsby", 39, 5),
//            new Book(2L, "Lolita", 25, 3),
//            new Book(3L, "The Catcher in the Rye", 22, 2),
//            new Book(4L, "Don Quixote", 42, 9),
//            new Book(5L, "The Grapes of Wrath", 33, 3),
//            new Book(6L, "Beloved", 17, 4),
//            new Book(7L, "Catch-22", 20, 6),
//            new Book(8L, "To Kill a Mockingbird", 25, 2),
//            new Book(9L, "Frankenstein", 15, 0),
//            new Book(10L, "Ulysses", 31, 1),
//            new Book(11L, "Alice in Wonderland", 25, 3),
//            new Book(12L, "Anna Karenina", 27, 1)
//
//
//    );



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
