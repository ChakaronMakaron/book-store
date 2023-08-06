package com.andersen;

import com.andersen.enums.RequestSortKey;
import com.andersen.models.Book;
import com.andersen.models.Request;
import com.andersen.repositories.RequestRepository;
import com.andersen.repositories.impl.RequestRepositoryDummy;
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

public class RequestControllerSortTest {
    private static Map<RequestSortKey, Pair<List<Request>>> sortKeyToActualExpectedPair;

    @ParameterizedTest
    @EnumSource(RequestSortKey.class)
    public void whenListCalled_withProvidedSortKey_thenSortedByKey(RequestSortKey sortKey) {
        Pair<List<Request>> pair = sortKeyToActualExpectedPair.get(sortKey);

        assertNotNull(pair);

        RequestRepository requestRepository = new RequestRepositoryDummy();
        pair.actual().forEach(requestRepository::add);
        List<Request> sortedRequests = requestRepository.findAllByClientIdSortedByKey(1L, sortKey);

        assertEquals(pair.expected(), sortedRequests);
    }

    @BeforeAll
    public static void fillMap() {
        List<Request> requestsForNameSort = Stream.of("B", "C", "Z", "X")
                .map(name -> new Request(1L, 1L, new Book(1L, name, 0, 0), 0))
                .collect(Collectors.toList());
        List<Request> requestsForNameSortCopy = new ArrayList<>(requestsForNameSort);
        requestsForNameSortCopy.sort(Comparator.comparing(request -> request.getBook().getName()));

        List<Request> requestsForPriceSort = Stream.of(1, 3, 4, 2)
                .map(price -> new Request(1L, 1L, new Book(1L, "", price, 0), price))
                .collect(Collectors.toList());
        List<Request> requestsForPriceSortCopy = new ArrayList<>(requestsForPriceSort);
        requestsForPriceSortCopy.sort(Comparator.comparing(request -> request.getBook().getPrice() * request.getAmount()));

        List<Request> requestsForNaturalOrder = Stream.of(1, 3, 4, 2)
                .map(price -> new Request(1L, 1L, new Book(1L, "", price, 0), price))
                .collect(Collectors.toList());
        List<Request> requestsForNaturalOrderCopy = new ArrayList<>(requestsForNaturalOrder);

        sortKeyToActualExpectedPair = new HashMap<>() {{
            put(RequestSortKey.NAME, new Pair<>(requestsForNameSort, requestsForNameSortCopy));
            put(RequestSortKey.PRICE, new Pair<>(requestsForPriceSort, requestsForPriceSortCopy));
            put(RequestSortKey.NATURAL, new Pair<>(requestsForNaturalOrder, requestsForNaturalOrderCopy));
        }};
    }
}
