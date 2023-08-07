package com.andersen.controllers;

import java.time.LocalDateTime;

public interface OrderController {

    void list(String sortKey);

    void complete(Long orderId);

    void create();

    void cancel(Long orderId);
    void countIncome(LocalDateTime startPeriodOfCompletionDate, LocalDateTime endPeriodOfCompletionDate);
}
