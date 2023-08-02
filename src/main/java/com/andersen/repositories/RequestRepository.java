package com.andersen.repositories;

import com.andersen.models.Request;

import java.util.ArrayList;
import java.util.List;

public class RequestRepository {

    private final List<Request> requests = new ArrayList<>();
    public List<Request> findAll(){
        return requests;
    }
    public void add(Request request){
        if(request == null){
            throw new IllegalArgumentException("Request is null");
        }
        requests.add(request);
    }
}
