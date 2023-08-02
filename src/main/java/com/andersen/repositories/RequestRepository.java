package com.andersen.repositories;

import com.andersen.models.Request;

import java.util.List;

public interface RequestRepository {
    List<Request> getAll();
}
