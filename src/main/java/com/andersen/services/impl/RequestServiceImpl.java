package com.andersen.services.impl;

import com.andersen.enums.RequestSortKey;
import com.andersen.models.Request;
import com.andersen.repositories.RequestRepository;
import com.andersen.services.RequestService;
import com.google.inject.Inject;

import java.util.List;

public class RequestServiceImpl implements RequestService {

    @Inject
    private RequestRepository requestRepository;

    @Override
    public List<Request> getAllSorted(RequestSortKey requestSortKey) {
        return requestRepository.getAllSorted(requestSortKey);
    }
}
