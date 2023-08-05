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

    public List<Order> findOrdersByClientId(Long clientId, OrderSortKey sortKey) {
        if (clientId < 1L) {
            throw new IllegalArgumentException("Bad order id");
        }
        List<Order> clientOrders = orders.stream()
                .filter(order -> order.getClientId().equals(clientId)).collect(Collectors.toList());
        if (sortKey != OrderSortKey.NATURAL) {
            sort(clientOrders, sortKey);
        }
        return clientOrders;
    }

    public List<Order> list(OrderSortKey sortKey) {
        List<Order> fetchedOrders = new ArrayList<>(orders);
        sort(fetchedOrders, sortKey);
        return fetchedOrders;
    }

    public static void sort(List<Order> orders, OrderSortKey orderSortKey) {

        switch (orderSortKey) {
            case PRICE -> orders.sort(Comparator.comparing(Order::getPrice));
            case DATE ->
                    orders.sort(Comparator.comparing(Order::getCompletionDate, Comparator.nullsLast(LocalDateTime::compareTo)));
            case STATUS -> orders.sort(Comparator.comparing(Order::getStatus));
        }

    }
}
