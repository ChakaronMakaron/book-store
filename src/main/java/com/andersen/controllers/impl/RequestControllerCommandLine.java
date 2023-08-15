package com.andersen.controllers.impl;

import com.andersen.authorization.Authenticator;
import com.andersen.controllers.RequestController;
import com.andersen.enums.RequestSortKey;
import com.andersen.models.Request;
import com.andersen.services.RequestService;
import com.andersen.services.impl.OrderServiceImpl;

public class RequestControllerCommandLine implements RequestController {

    private final RequestService requestService;

    public RequestControllerCommandLine(RequestService requestService) {
        this.requestService = requestService;
    }

    @Override
    public void list(String sortKey) {
        Long clientId = Authenticator.getInstance().getUser().getId();

        RequestSortKey requestSortKey = RequestSortKey.valueOf(sortKey.toUpperCase());
        requestService.getAllByClientIdSortedByKey(clientId, requestSortKey).forEach(System.out::println);
    }

    @Override
    public void requestCreationSwitch(Boolean choice) {
        requestService.setRequestCreationAvailability(choice);
    }

}
