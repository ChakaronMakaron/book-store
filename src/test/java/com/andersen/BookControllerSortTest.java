package com.andersen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;
import com.andersen.repositories.BookRepository;

public class BookControllerSortTest {
    private static Map<BookSortKey, Pair<List<Book>>> sortKeyToActualExpectedPair;

    @Test
    public void testIfAllSortKeysCanBeTested() {
        for (BookSortKey bookSortKey : BookSortKey.values()) {
            Assertions.assertNotNull(sortKeyToActualExpectedPair.get(bookSortKey));
        }
    }

    @Test
    public void whenSortCalled_withNameSortKey_thenSortedByName() {
        Pair<List<Book>> actualExpectedPair = sortKeyToActualExpectedPair.get(BookSortKey.NAME);
        BookRepository.sort(actualExpectedPair.actual(), BookSortKey.NAME);
        Assertions.assertEquals(actualExpectedPair.expected(), actualExpectedPair.actual());
    }

    @Test
    public void whenSortCalled_withPriceSortKey_thenSortedByPrice() {
        Pair<List<Book>> actualExpectedPair = sortKeyToActualExpectedPair.get(BookSortKey.PRICE);
        BookRepository.sort(actualExpectedPair.actual(), BookSortKey.PRICE);
        Assertions.assertEquals(actualExpectedPair.expected(), actualExpectedPair.actual());
    }

    @Test
    public void whenSortCalled_withAmountSortKey_thenSortedByAmount() {
        Pair<List<Book>> actualExpectedPair = sortKeyToActualExpectedPair.get(BookSortKey.AMOUNT);
        BookRepository.sort(actualExpectedPair.actual(), BookSortKey.AMOUNT);
        Assertions.assertEquals(actualExpectedPair.expected(), actualExpectedPair.actual());
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

        sortKeyToActualExpectedPair = new HashMap<>() {{
            put(BookSortKey.NAME, new Pair<>(booksForNameSort, booksForNameSortCopy));
            put(BookSortKey.PRICE, new Pair<>(booksForPriceSort, booksForPriceSortCopy));
            put(BookSortKey.AMOUNT, new Pair<>(booksForAmountSort, booksForAmountSortCopy));
        }};
    }
}