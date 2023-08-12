package com.andersen.controllers;

import java.util.List;

import com.andersen.models.Order;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface OrderController {

    List<Order> list(HttpServletRequest request, HttpServletResponse response);

    void complete(HttpServletRequest request, HttpServletResponse response);

    void create(HttpServletRequest request, HttpServletResponse response);

    void cancel(HttpServletRequest request, HttpServletResponse response);

    void countIncome(HttpServletRequest request, HttpServletResponse response);
}
