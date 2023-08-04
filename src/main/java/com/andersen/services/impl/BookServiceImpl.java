package com.andersen.services.impl;

import com.andersen.models.Book;
import com.andersen.repositories.BookRepository;
import com.andersen.services.BookService;

import java.util.List;

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
    public Book getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public void changeAmountOfBook(Long bookId, Integer amount) {
        if(amount < 0){
            throw new IllegalArgumentException("Amount value is not valid");
        }
        bookRepository.findById(bookId).setAmount(amount);
    }

    @Override
    public void changeBookStatus(Long bookId, boolean bookStatus) {
        if(bookStatus){
            bookRepository.findById(bookId).setStatus(Book.BookStatus.IN_STOCK);
        } else {
            bookRepository.findById(bookId).setStatus(Book.BookStatus.OUT_OF_STOCK);
        }
    }
}
