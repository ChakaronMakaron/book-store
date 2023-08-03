package com.andersen.services.impl;

import com.andersen.models.Book;
import com.andersen.repositories.BookRepository;
import com.andersen.services.BookService;

import java.util.List;
import java.util.Optional;

public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public void changeAmountOfBook(Long bookId, Integer amount) {
        if(amount < 0){
            throw new IllegalArgumentException("Amount value is not valid");
        }
        bookRepository.findById(bookId).ifPresent(book -> book.setAmount(amount));
    }
}
