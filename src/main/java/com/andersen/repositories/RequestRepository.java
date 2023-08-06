package com.andersen.repositories;

import com.andersen.enums.RequestSortKey;
import com.andersen.models.Request;

import java.util.List;

public interface RequestRepository {
    List<Request> list(RequestSortKey sortKey);

    List<Request> findAll();

    void add(Request request);
}
