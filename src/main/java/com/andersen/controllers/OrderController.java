package com.andersen.controllers;

import com.andersen.models.Order;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderController {
    List<Order> getAllSorted(HttpServletRequest request, HttpServletResponse response);

    void addOrder(Order order);

    void complete(Long id);

    int getIncomeBySpecifiedPeriod(LocalDateTime from, LocalDateTime to);
}
