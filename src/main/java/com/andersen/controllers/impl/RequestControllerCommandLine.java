package com.andersen.controllers.impl;

import com.andersen.controllers.RequestController;
import com.andersen.enums.BookSortKey;
import com.andersen.enums.RequestSortKey;
import com.andersen.models.Book;
import com.andersen.models.Request;
import com.andersen.services.RequestService;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RequestControllerCommandLine implements RequestController {
    private final RequestService requestService;

    public RequestControllerCommandLine(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public void list(String sortKey) {
        List<Request> requests = requestService.getAll();

        try {
            RequestSortKey requestSortKey = RequestSortKey.valueOf(sortKey.toUpperCase());

            sort(requests, requestSortKey);

            requests.forEach(System.out::println);
        } catch (IllegalArgumentException e) {
            System.out.printf("Wrong sort key (valid sorting keys - %s)",
                    Arrays.stream(RequestSortKey.values())
                            .map(RequestSortKey::toString)
                            .collect(Collectors.joining(", ", "", ".")));
        }
    }

    public static void sort(List<Request> requests, RequestSortKey requestSortKey) {
        switch (requestSortKey) {
            case NAME -> requests.sort(Comparator.comparing(request -> request.getBook().getName()));
            case PRICE -> requests.sort(Comparator.comparing(request -> request.getBook().getPrice() * request.getAmount()));
        }
    }
}
