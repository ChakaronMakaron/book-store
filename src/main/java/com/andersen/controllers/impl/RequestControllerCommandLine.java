package com.andersen.controllers.impl;

import com.andersen.controllers.RequestController;
import com.andersen.services.RequestService;
import com.google.inject.Inject;

public class RequestControllerCommandLine implements RequestController {

    @Inject
    private RequestService requestService;
}
