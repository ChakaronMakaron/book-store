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

import com.andersen.enums.RequestSortKey;
import com.andersen.models.Book;
import com.andersen.models.Request;
import com.andersen.repositories.impl.RequestRepositoryDummy;

public class RequestControllerSortTest {
    private static Map<RequestSortKey, Pair<List<Request>>> sortKeyToActualExpectedPair;

    @Test
    public void testIfAllSortKeysCanBeTested() {
        for (RequestSortKey requestSortKey : RequestSortKey.values()) {
            Assertions.assertNotNull(sortKeyToActualExpectedPair.get(requestSortKey));
        }
    }

    @Test
    public void whenSortCalled_withNameSortKey_thenSortedByName() {
        Pair<List<Request>> actualExpectedPair = sortKeyToActualExpectedPair.get(RequestSortKey.NAME);
        RequestRepositoryDummy.sort(actualExpectedPair.actual(), RequestSortKey.NAME);
        Assertions.assertEquals(actualExpectedPair.expected(), actualExpectedPair.actual());
    }

    @Test
    public void whenSortCalled_withPriceSortKey_thenSortedByPrice() {
        Pair<List<Request>> actualExpectedPair = sortKeyToActualExpectedPair.get(RequestSortKey.PRICE);
        RequestRepositoryDummy.sort(actualExpectedPair.actual(), RequestSortKey.PRICE);
        Assertions.assertEquals(actualExpectedPair.expected(), actualExpectedPair.actual());
    }

    @BeforeAll
    public static void fillMap() {
        List<Request> requestsForNameSort = Stream.of("B", "C", "Z", "X")
                .map(name -> new Request(0L, 0L, new Book(0L, name, 0, 0), 0))
                .collect(Collectors.toList());
        List<Request> requestsForNameSortCopy = new ArrayList<>(requestsForNameSort);
        requestsForNameSortCopy.sort(Comparator.comparing(request -> request.getBook().getName()));

        List<Request> requestsForPriceSort = Stream.of(1, 3, 4, 2)
                .map(price -> new Request(0L, 0L, new Book(0L, "", price, 0), price))
                .collect(Collectors.toList());
        List<Request> requestsForPriceSortCopy = new ArrayList<>(requestsForPriceSort);
        requestsForPriceSortCopy.sort(Comparator.comparing(request -> request.getBook().getPrice() * request.getAmount()));

        sortKeyToActualExpectedPair = new HashMap<>() {{
            put(RequestSortKey.NAME, new Pair<>(requestsForNameSort, requestsForNameSortCopy));
            put(RequestSortKey.PRICE, new Pair<>(requestsForPriceSort, requestsForPriceSortCopy));
        }};
    }
}
