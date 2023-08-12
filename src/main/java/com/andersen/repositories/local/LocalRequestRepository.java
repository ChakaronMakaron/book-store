package com.andersen.repositories.local;

import com.andersen.config.model.ConfigModel;
import com.andersen.enums.RequestSortKey;
import com.andersen.models.Request;
import com.andersen.repositories.RequestRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

public class LocalRequestRepository implements RequestRepository {
    @Inject
    private ObjectMapper objectMapper;
    @Inject
    private ConfigModel configModel;

    @Override
    public void save(Request request) {
        List<Request> requests = getAll();

        requests.add(request);

        save(requests);
    }

    @Override
    public List<Request> getAll() {
        try {
            return objectMapper.readValue(new File(configModel.savePath()), new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Request> getAllSorted(RequestSortKey sortKey) {
        List<Request> requests = getAll();

        if (sortKey == RequestSortKey.NATURAL) return requests;

        sort(requests, sortKey);
        return requests;
    }

    public Request findById(List<Request> requests, Long id) {
        for (Request request : requests) {
            if (Objects.equals(request.getId(), id)) {
                return request;
            }
        }
        throw new IllegalArgumentException("Wrong book id");
    }

    @Override
    public void changeRequestStatus(Long id, Request.RequestStatus status) {
        List<Request> requests = getAll();

        Request searchedRequest = findById(requests, id);

        searchedRequest.setRequestStatus(status);

        save(requests);
    }

    public void sort(List<Request> request, RequestSortKey sortKey) {
        switch (sortKey) {
            case PRICE -> request.sort(Comparator.comparing(requestToSort -> requestToSort.getBook().getName()));
            default -> throw new IllegalArgumentException("Unexpected value: " + sortKey);
        }
    }

    public void save(List<Request> requests) {
        try {
            AppState appState = objectMapper.readValue(new File(configModel.savePath()), AppState.class);

            appState.synchronizeRequests(requests);

            objectMapper.writeValue(new File(configModel.savePath()), appState);
            ;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
