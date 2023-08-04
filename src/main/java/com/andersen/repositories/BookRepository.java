package com.andersen.repositories;

import com.andersen.models.Book;

import java.util.List;

public class BookRepository {

    private final List<Book> books = List.of(
            new Book(1L,"The Great Gatsby", 39, 5),
            new Book(2L, "Lolita", 25, 3),
            new Book(3L, "The Catcher in the Rye", 22, 2),
            new Book(4L, "Don Quixote", 42, 9),
            new Book(5L, "The Grapes of Wrath", 33, 3),
            new Book(6L, "Beloved", 17, 4),
            new Book(7L, "Catch-22", 20, 6),
            new Book(8L, "To Kill a Mockingbird", 25, 2),
            new Book(9L, "Frankenstein", 15, 0),
            new Book(10L, "Ulysses", 31, 1),
            new Book(11L, "Alice in Wonderland", 25, 3),
            new Book(12L, "Anna Karenina", 27, 1)


    );




    public List<Book> findAll(){
        return books;
    }

    public Book findById(Long id) {
        if(id < 1L){
            throw new IllegalArgumentException("Bad book id");
        }
        for(Book book : books){
            if(book.getId().equals(id)){
                return book;
            }
        }
        return null;
    }

}
