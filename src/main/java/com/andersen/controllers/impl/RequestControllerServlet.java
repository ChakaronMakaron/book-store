package com.andersen.controllers.impl;

import com.andersen.controllers.RequestController;
import com.andersen.services.RequestService;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class RequestControllerServlet implements RequestController {

    private final RequestService requestService;

    @Inject
    public RequestControllerServlet(RequestService requestService) {
        this.requestService = requestService;
    }
}
