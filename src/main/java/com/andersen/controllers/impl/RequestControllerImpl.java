package com.andersen.controllers.impl;

import com.andersen.controllers.RequestController;
import com.andersen.models.Request;
import com.andersen.services.RequestService;

import java.util.Comparator;

import static java.lang.System.out;

public class RequestControllerImpl implements RequestController {
    private final RequestService requestService;

    public RequestControllerImpl(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public void list(String sortKey) {
        switch (sortKey) {
            case "name":
                requestService.getAll().stream()
                        .sorted(Comparator.comparing(request -> request.getBook().getName()))
                        .forEach(out::println);
                break;
            case "price":
                requestService.getAll().stream()
                        .sorted(Comparator.comparing(Request::getAmount))
                        .forEach(out::println);
                break;
        }
    }
}
