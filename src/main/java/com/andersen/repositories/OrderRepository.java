package com.andersen.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.andersen.models.Order;

public interface OrderRepository {
    List<Order> findAll();

    void add(Order order);

    Optional<Order> findByOrderId(Long orderId);

    List<Order> findOrdersByClientId(Long clientId);

    List<Order> list(String sortKey);

    List<Order> findOrdersInPeriodOfCompletionDateWithPositiveStatus(LocalDateTime startCompletionDate, LocalDateTime endCompletionDate);

    void sort(List<Order> orders, String orderSortKey);
}
