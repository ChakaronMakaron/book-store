package com.andersen.services.impl;

import java.util.List;
import java.util.Optional;

import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;
import com.andersen.repositories.BookRepository;
import com.andersen.services.BookService;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Inject
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public List<Book> list(BookSortKey sortKey) {
        return bookRepository.list(sortKey);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findByBookId(bookId);
    }

    @Override
    public void changeAmountOfBook(Long bookId, Integer amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount value is not valid");
        }
        bookRepository.findByBookId(bookId).ifPresent(book -> book.setAmount(amount));
    }

    @Override
    public void changeBookStatus(Long bookId, boolean bookStatus) {
        if (bookStatus) {
            bookRepository.findByBookId(bookId).orElseThrow(IllegalArgumentException::new).setStatus(Book.BookStatus.IN_STOCK);
        } else {
            bookRepository.findByBookId(bookId).orElseThrow(IllegalArgumentException::new).setStatus(Book.BookStatus.OUT_OF_STOCK);
        }
    }
}
