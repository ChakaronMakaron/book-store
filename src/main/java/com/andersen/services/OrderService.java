package com.andersen.services;

import com.andersen.models.Order;

import java.util.List;

public interface OrderService {
    List<Order> list();

    void add(Order order);

    void save(Order order);

    List<Order> getAllClientOrders(Long clientId);

    void changeStatus(Long orderId, Order.OrderStatus newStatus);

    void processOrder(Order order);

    void processUserInput(Order order, String bookRequest);

    void sort(List<Order> orders, String orderSortKey);

    void changeRequestsStatusByOrderStatus(Order.OrderStatus newStatus, Order order);

}
