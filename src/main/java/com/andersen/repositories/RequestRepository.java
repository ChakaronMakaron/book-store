package com.andersen.repositories;

import com.andersen.enums.RequestSortKey;
import com.andersen.models.Request;

import java.util.List;

public interface RequestRepository {
    List<Request> list(RequestSortKey sortKey);

    List<Request> findAll();

    List<Request> findAllByClientIdSortedByKey(Long clientId, RequestSortKey requestSortKey);

    void add(Request request);
}
