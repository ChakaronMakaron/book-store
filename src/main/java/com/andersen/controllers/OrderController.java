package com.andersen.controllers;

import com.andersen.models.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderController {

    List<Order> list(String sortKey);

    void complete(Long orderId);

    void create();

    void cancel(Long orderId);

    void countIncome(LocalDateTime startPeriodOfCompletionDate, LocalDateTime endPeriodOfCompletionDate);

    void orderRequestCreationSwitch(Boolean choice);
}
