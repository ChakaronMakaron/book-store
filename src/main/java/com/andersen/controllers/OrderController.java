package com.andersen.controllers;

import com.andersen.models.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderController {
    List<Order> getAllSorted(String sortKey);

    void addOrder(Order order);

    void complete(Long id);

    int getIncomeBySpecifiedPeriod(LocalDateTime from, LocalDateTime to);
}
