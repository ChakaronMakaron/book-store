package com.andersen.repositories;

import java.util.ArrayList;
import java.util.List;

import com.andersen.models.Request;

public class RequestRepository {

    private final List<Request> requests = new ArrayList<>();

    public List<Request> findAll() {
        return requests;
    }

    public void add(Request request) {
        if (request == null) {
            throw new IllegalArgumentException("Request is null");
        }
        requests.add(request);
    }
}
