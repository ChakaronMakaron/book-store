package com.andersen.repositories.impl;

import com.andersen.Pair;
import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;
import com.andersen.repositories.BookRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BookRepositoryDummyTest {
    private static Map<BookSortKey, Pair<List<Book>>> sortKeyToActualExpectedPair;

    @ParameterizedTest
    @EnumSource(BookSortKey.class)
    public void whenListCalled_withProvidedSortKey_thenSortedByKey(BookSortKey sortKey) {
        Pair<List<Book>> pair = sortKeyToActualExpectedPair.get(sortKey);

        assertNotNull(pair);

        BookRepository bookRepository = new BookRepositoryDummy(pair.actual());
        List<Book> sortedBooks = bookRepository.list(sortKey);

        assertEquals(pair.expected(), sortedBooks);
    }

    @BeforeAll
    public static void fillMap() {
        List<Book> booksForNameSort = Stream.of("B", "C", "Z", "X")
                .map(name -> new Book(0L, name, 0, 0))
                .collect(Collectors.toList());
        List<Book> booksForNameSortCopy = new ArrayList<>(booksForNameSort);
        booksForNameSortCopy.sort(Comparator.comparing(Book::getName));

        List<Book> booksForPriceSort = Stream.of(12, 35, 1, 0)
                .map(price -> new Book(0L, "", price, 0))
                .collect(Collectors.toList());
        List<Book> booksForPriceSortCopy = new ArrayList<>(booksForPriceSort);
        booksForPriceSortCopy.sort(Comparator.comparing(Book::getPrice));

        List<Book> booksForAmountSort = Stream.of(2, 3, 1, 0, 4)
                .map(amount -> new Book(0L, "", 0, amount))
                .collect(Collectors.toList());
        List<Book> booksForAmountSortCopy = new ArrayList<>(booksForAmountSort);
        booksForAmountSortCopy.sort(Comparator.comparing(Book::getAmount));

        List<Book> booksForNaturalOrder = Stream.of(2, 3, 1, 0, 4)
                .map(amount -> new Book(0L, "", 0, amount))
                .collect(Collectors.toList());
        List<Book> booksForNaturalOrderCopy = new ArrayList<>(booksForNaturalOrder);

        sortKeyToActualExpectedPair = new HashMap<>() {{
            put(BookSortKey.NAME, new Pair<>(booksForNameSort, booksForNameSortCopy));
            put(BookSortKey.PRICE, new Pair<>(booksForPriceSort, booksForPriceSortCopy));
            put(BookSortKey.AMOUNT, new Pair<>(booksForAmountSort, booksForAmountSortCopy));
            put(BookSortKey.NATURAL, new Pair<>(booksForNaturalOrder, booksForNaturalOrderCopy));
        }};
    }
}
