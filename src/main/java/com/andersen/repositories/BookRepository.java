package com.andersen.repositories;

import com.andersen.models.Book;

import java.util.List;
import java.util.Optional;

public class BookRepository {

    private final List<Book> books;

    public BookRepository(List<Book> books) {
        this.books = books;
    }

    public List<Book> findAll(){
        return books;
    }

    public Optional<Book> findByBookId(Long bookId) {
        if(bookId < 1L){
            throw new IllegalArgumentException("Bad book id");
        }
        return books.stream()
                .filter(book -> book.getId().equals(bookId))
                .findFirst();
    }
}
