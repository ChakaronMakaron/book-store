package com.andersen.repositories.impl;

import com.andersen.enums.OrderSortKey;
import com.andersen.models.Order;
import com.andersen.repositories.OrderRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderRepositoryDummy implements OrderRepository {

    private final List<Order> orders = new ArrayList<>();

    @Override
    public List<Order> findAll() {
        return orders;
    }

    @Override
    public void add(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("Order is null");
        }
        orders.add(order);
    }

    @Override
    public Optional<Order> findByOrderId(Long orderId) {
        if (orderId < 1L) {
            throw new IllegalArgumentException("Bad order id");
        }
        return orders.stream()
                .filter(order -> order.getId().equals(orderId))
                .findFirst();
    }

    @Override
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

    @Override
    public List<Order> list(OrderSortKey sortKey) {
        List<Order> fetchedOrders = new ArrayList<>(orders);
        sort(fetchedOrders, sortKey);
        return fetchedOrders;
    }

    private void sort(List<Order> orders, OrderSortKey orderSortKey) {
        switch (orderSortKey) {
            case PRICE -> orders.sort(Comparator.comparing(Order::getPrice));
            case DATE ->
                    orders.sort(Comparator.comparing(Order::getCompletionDate, Comparator.nullsLast(LocalDateTime::compareTo)));
            case STATUS -> orders.sort(Comparator.comparing(Order::getStatus));
        }
    }
}