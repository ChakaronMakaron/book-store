package com.andersen.services.impl;

import com.andersen.enums.BookSortKey;
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
    public List<Book> getAllSorted(BookSortKey sortKey) {
        return bookRepository.getAllSorted(sortKey);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public void supply(Long bookId, Integer amount) {
        bookRepository.supply(bookId, amount);
    }

    @Override
    public void changeBookStatus(Long bookId, boolean bookStatus) {
        bookRepository.changeBookStatus(bookId, bookStatus ? Book.BookStatus.IN_STOCK : Book.BookStatus.OUT_OF_STOCK);
    }
}
