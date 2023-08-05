package com.andersen.repositories;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.andersen.enums.RequestSortKey;
import com.andersen.models.Request;

public class RequestRepository {

    private final List<Request> requests = new ArrayList<>();

    public List<Request> list(RequestSortKey sortKey) {
        List<Request> fetchedRequests = new ArrayList<>(requests);
        sort(fetchedRequests, sortKey);
        return fetchedRequests;
    }

    public List<Request> findAll() {
        return requests;
    }

    public void add(Request request) {
        if (request == null) {
            throw new IllegalArgumentException("Request is null");
        }
        requests.add(request);
    }

    public static void sort(List<Request> requests, RequestSortKey requestSortKey) {
        switch (requestSortKey) {
            case NAME -> requests.sort(Comparator.comparing(request -> request.getBook().getName()));
            case PRICE ->
                requests.sort(Comparator.comparing(request -> request.getBook().getPrice() * request.getAmount()));
        }
    }
}
