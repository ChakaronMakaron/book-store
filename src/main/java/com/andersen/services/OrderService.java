package com.andersen.services;

import com.andersen.models.Order;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {
    List<Order> list(String sortKey);

    void add(Order order);

    void save(Order order);

    List<Order> getAllClientOrders(Long clientId, String sortKey);

    void changeStatus(Long orderId, Order.OrderStatus newStatus);

    void processOrder(Order order);

    void processUserInput(Order order, String bookRequest);

    void changeRequestsStatusByOrderStatus(Order.OrderStatus newStatus, Order order);

    List<Order> getOrdersFilteredInPeriod(LocalDateTime startCompletionDate, LocalDateTime endCompletionDate);

    void setRequestCreationAvailabilityInOrder(boolean choice);

}
