package com.andersen.controllers;

import com.andersen.models.Book;

import java.util.List;

public interface BookController {

    List<Book> list(String sortKey);

    void bookSupply(Long id, int amountToAdd);
}
