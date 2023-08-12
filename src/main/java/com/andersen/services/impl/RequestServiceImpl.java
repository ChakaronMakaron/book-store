package com.andersen.services.impl;

import java.util.List;

import com.andersen.enums.RequestSortKey;
import com.andersen.models.Request;
import com.andersen.repositories.RequestRepository;
import com.andersen.services.RequestService;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;

@Singleton
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    @Inject
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
    public List<Request> getAllByClientIdSortedByKey(Long clientId, RequestSortKey requestSortKey) {
        return requestRepository.findAllByClientIdSortedByKey(clientId, requestSortKey);
    }

    @Override
    public void add(Request request) {
        requestRepository.add(request);
    }
}
