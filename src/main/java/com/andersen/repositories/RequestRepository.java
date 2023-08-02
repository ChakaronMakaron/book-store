package com.andersen.repositories;

import com.andersen.models.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestRepository {

    private List<Request> requests = new ArrayList<>();
    public List<Request> findAll(){
        return requests;
    }
}
