package com.andersen.controllers.impl;

import com.andersen.controllers.BookController;
import com.andersen.models.Book;
import com.andersen.services.BookService;


public class BookControllerImpl implements BookController{
    private final BookService bookService;

    public BookControllerImpl(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    public void list(String sortKey) {

    }

    @Override
    public void add(String name, int amountToAdd, int priceToAdd) {

    }

    public void changeBookStatus(Long bookId, boolean bookStatus){
        bookService.changeBookStatus(bookId, bookStatus);
    }
}
