package com.andersen.controllers.impl;

import com.andersen.authorization.Authenticator;
import com.andersen.controllers.RequestController;
import com.andersen.enums.RequestSortKey;
import com.andersen.models.Request;
import com.andersen.services.RequestService;

import java.util.List;

public class RequestControllerCommandLine implements RequestController {

    private final RequestService requestService;

    public RequestControllerCommandLine(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public List<Request> list(String sortKey) {
        Long clientId = Authenticator.getInstance().getUser().getId();

        RequestSortKey requestSortKey = RequestSortKey.valueOf(sortKey.toUpperCase());
        List<Request> requests = requestService.getAllByClientIdSortedByKey(clientId, requestSortKey);
        requests.forEach(System.out::println);
        return requests;
    }
}
