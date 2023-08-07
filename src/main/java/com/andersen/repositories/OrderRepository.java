package com.andersen.repositories;

import com.andersen.enums.OrderSortKey;
import com.andersen.models.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Optional<Order> findByOrderId(Long orderId) {
        if (orderId < 1L) {
            throw new IllegalArgumentException("Bad order id");
        }
        return orders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst();
    }

    public List<Order> findOrdersByClientId(Long clientId) {
        if (clientId < 1L) {
            throw new IllegalArgumentException("Bad order id");
        }
        return orders.stream()
                .filter(order -> order.getClientId().equals(clientId)).collect(Collectors.toList());
    }

    public void sort(List<Order> orders, String orderSortKey) {

        OrderSortKey sortKey = OrderSortKey.valueOf(orderSortKey.toUpperCase());

        switch (sortKey) {
            case PRICE -> orders.sort(Comparator.comparing(Order::getPrice));
            case DATE ->
                    orders.sort(Comparator.comparing(Order::getCompletionDate, Comparator.nullsLast(LocalDateTime::compareTo)));
            case STATUS -> orders.sort(Comparator.comparing(Order::getStatus));
        }

    }
}
