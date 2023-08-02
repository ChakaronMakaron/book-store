package com.andersen.repositories;

import com.andersen.models.Book;

import java.util.List;

public interface BookRepository {
    List<Book> getAll();
}
