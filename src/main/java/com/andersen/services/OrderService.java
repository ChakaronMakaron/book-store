package com.andersen.services;

import com.andersen.models.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();

    void add(Order order);

    void save(Order order);

    List<Order> getAllClientOrders(Long clientId);

    void changeStatus(Long orderId, Order.OrderStatus newStatus);
}
