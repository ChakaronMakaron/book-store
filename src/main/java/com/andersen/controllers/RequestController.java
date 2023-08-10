package com.andersen.controllers;

import com.andersen.models.Request;

import java.util.List;

public interface RequestController {

    List<Request> list(String sortKey);
}
