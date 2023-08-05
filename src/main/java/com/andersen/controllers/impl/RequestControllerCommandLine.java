package com.andersen.controllers.impl;

import com.andersen.controllers.RequestController;
import com.andersen.models.Request;
import com.andersen.services.RequestService;

import java.util.Comparator;
import java.util.List;

public class RequestControllerCommandLine implements RequestController {
    private final RequestService requestService;

    public RequestControllerCommandLine(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public void list(String sortKey) {
        List<Request> requests = requestService.getAll();

        switch (sortKey) {
            case "name" -> requests.sort(Comparator.comparing(request -> request.getBook().getName()));
            case "price" ->
                    requests.sort(Comparator.comparing(request -> request.getBook().getPrice() * request.getAmount()));
        }

        requests.forEach(System.out::println);
    }
}
