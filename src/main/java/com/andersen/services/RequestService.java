package com.andersen.services;

import com.andersen.enums.RequestSortKey;
import com.andersen.models.Request;

import java.util.List;

public interface RequestService {
    List<Request> list(RequestSortKey sortKey);

    List<Request> getAllByClientIdSortedByKey(Long clientId, RequestSortKey requestSortKey);

    List<Request> getAll();

    void add(Request request);
}
