package com.andersen.repositories;

import java.util.ArrayList;
import java.util.List;

import com.andersen.models.Order;

public class OrderRepository {

    private final List<Order> orders = new ArrayList<>();

    public List<Order> findAll() {
        return orders;
    }

    public void add(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order is null");
        }
        orders.add(order);
    }

    public Order findById(Long bookId) {
        if (bookId < 1L) {
            throw new IllegalArgumentException("Bad order id");
        }
        for (Order order : orders) {
            if (order.getId().equals(bookId)) {
                return order;
            }
        }
        return null;
    }

    public void remove(Long bookId) {
        for (Order order : orders) {
            if (order.getId().equals(bookId)) {
                orders.remove(order);
            }
        }
    }

    public List<Order> findOrdersByClientId(Long clientId) {
        if (clientId < 1L) {
            throw new IllegalArgumentException("Bad order id");
        }
        List<Order> ordersOfClient = new ArrayList<>();
        for (Order order : orders) {
            if (order.getClientId().equals(clientId)) {
                ordersOfClient.add(order);
            }
        }
        return ordersOfClient;
    }
}
