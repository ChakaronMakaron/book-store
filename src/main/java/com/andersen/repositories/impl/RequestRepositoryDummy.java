package com.andersen.repositories.impl;

import com.andersen.enums.RequestSortKey;
import com.andersen.models.Request;
import com.andersen.repositories.RequestRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RequestRepositoryDummy implements RequestRepository {

    private final List<Request> requests = new ArrayList<>();

    @Override
    public List<Request> list(RequestSortKey sortKey) {
        List<Request> fetchedRequests = new ArrayList<>(requests);
        sort(fetchedRequests, sortKey);
        return fetchedRequests;
    }

    @Override
    public List<Request> findAll() {
        return requests;
    }

    @Override
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
