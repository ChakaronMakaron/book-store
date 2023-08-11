package com.andersen.repositories.local;

import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;
import com.andersen.repositories.BookRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class LocalBookRepository implements BookRepository {
    private final ObjectMapper objectMapper;
    private final String savePath;

    public LocalBookRepository(ObjectMapper objectMapper, String savePath) {
        this.objectMapper = objectMapper;
        this.savePath = savePath;
    }

    @Override
    public List<Book> getAll() {
        try {
            return objectMapper.readValue(new File(savePath), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        return getAll().stream().filter(book -> Objects.equals(book.getId(), bookId)).findFirst();
    }

    public Book findById(List<Book> books, Long id) {
        for (Book book : books) {
            if (Objects.equals(book.getId(), id)) {
                return book;
            }
        }
        throw new IllegalArgumentException("Wrong book id");
    }

    @Override
    public List<Book> getAllSorted(BookSortKey sortKey) {
        List<Book> books = getAll();

        if (sortKey == BookSortKey.NATURAL) return books;

        sort(books, sortKey);
        return books;
    }

    @Override
    public void supply(Long id, int amount) {
        List<Book> books = getAll();

        Book suppliedBook = findById(books, id);

        suppliedBook.setAmount(suppliedBook.getAmount() + amount);

        save(books);
    }

    @Override
    public void changeBookStatus(Long id, Book.BookStatus status) {
        List<Book> books = getAll();

        Book searchedBook = findById(books, id);

        searchedBook.setStatus(status);

        save(books);
    }

    private void sort(List<Book> books, BookSortKey bookSortKey) {
        switch (bookSortKey) {
            case NAME -> books.sort(Comparator.comparing(Book::getName));
            case PRICE -> books.sort(Comparator.comparing(Book::getPrice));
            case AMOUNT -> books.sort(Comparator.comparing(Book::getAmount));
            default -> throw new IllegalArgumentException("Unexpected value: " + bookSortKey);
        }
    }

    private void save(List<Book> books) {
        try {
            objectMapper.writeValue(new File(savePath), books);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
