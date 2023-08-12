package com.andersen.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RequestController {

    void list(HttpServletRequest request, HttpServletResponse response);
}
