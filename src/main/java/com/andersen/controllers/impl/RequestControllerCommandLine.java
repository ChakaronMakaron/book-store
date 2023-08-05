package com.andersen.controllers.impl;

import com.andersen.controllers.RequestController;
import com.andersen.enums.RequestSortKey;
import com.andersen.services.RequestService;

public class RequestControllerCommandLine implements RequestController {

    private final RequestService requestService;

    public RequestControllerCommandLine(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public void list(String sortKey) {
        RequestSortKey requestSortKey = RequestSortKey.valueOf(sortKey.toUpperCase());
        requestService.list(requestSortKey).forEach(System.out::println);
    }
}
