package com.andersen.services.impl;

import com.andersen.enums.RequestSortKey;
import com.andersen.models.Request;
import com.andersen.repositories.RequestRepository;
import com.andersen.repositories.impl.RequestRepositoryDummy;
import com.andersen.services.RequestService;

import java.util.List;

public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    @Override
    public List<Request> getAll() {
        return requestRepository.findAll();
    }

    @Override
    public List<Request> list(RequestSortKey sortKey) {
        return requestRepository.list(sortKey);
    }

    @Override
    public void add(Request request) {
        requestRepository.add(request);
    }
}
