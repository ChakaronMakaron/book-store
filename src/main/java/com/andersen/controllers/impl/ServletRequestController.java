package com.andersen.controllers.impl;

import com.andersen.controllers.RequestController;
import com.andersen.services.RequestService;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Singleton
public class ServletRequestController implements RequestController {

    private final RequestService requestService;

    @Inject
    public ServletRequestController(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public void list(HttpServletRequest request, HttpServletResponse response) {
        /*
        // Long clientId = Authenticator.getInstance().getUser().getId(); // TODO

        Long clientId = 1L;

        RequestSortKey requestSortKey = RequestSortKey.valueOf(sortKey.toUpperCase());
        requestService.getAllByClientIdSortedByKey(clientId, requestSortKey).forEach(System.out::println);
        */
    }
}
