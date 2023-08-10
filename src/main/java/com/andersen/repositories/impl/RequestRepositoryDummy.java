package com.andersen.repositories.impl;

import com.andersen.enums.RequestSortKey;
import com.andersen.models.Request;
import com.andersen.repositories.RequestRepository;
import com.andersen.utils.JSONParserClass;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RequestRepositoryDummy implements RequestRepository {

    private final List<Request> requests;
    private final JSONParserClass JsonParser;

    public RequestRepositoryDummy(List<Request> requests) {
        this.requests = requests;
        JsonParser = new JSONParserClass();
    }

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
    public List<Request> findAllByClientIdSortedByKey(Long clientId, RequestSortKey sortKey) {
        if (clientId < 1L) {
            throw new IllegalArgumentException("Bad order id");
        }
        List<Request> clientRequests = requests.stream()
                .filter(request -> request.getClientId().equals(clientId)).collect(Collectors.toList());
        if (sortKey != RequestSortKey.NATURAL) {
            sort(clientRequests, sortKey);
        }
        return clientRequests;
    }

    @Override
    public void add(Request request) {
        if (request == null) {
            throw new IllegalArgumentException("Request is null");
        }
        requests.add(request);
        JsonParser.writeJsonRequests(requests);
    }

    private void sort(List<Request> requests, RequestSortKey requestSortKey) {
        switch (requestSortKey) {
            case NAME -> requests.sort(Comparator.comparing(request -> request.getBook().getName()));
            case PRICE ->
                    requests.sort(Comparator.comparing(request -> request.getBook().getPrice() * request.getAmount()));
        }
    }
}
