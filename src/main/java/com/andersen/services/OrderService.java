package com.andersen.services;

import com.andersen.enums.OrderSortKey;
import com.andersen.models.Order;

import java.util.List;

public interface OrderService {
    List<Order> getAll();

    List<Order> list(OrderSortKey sortKey);

    void add(Order order);

    void save(Order order);

    List<Order> getAllClientOrders(Long clientId, OrderSortKey sortKey);

    void changeStatus(Long orderId, Order.OrderStatus newStatus);
}
