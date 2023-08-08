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

    private final List<Order> orders;

    public OrderRepositoryDummy(List<Order> orders) {
        this.orders = orders;
    }

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
    public List<Order> findOrdersByClientId(Long clientId) {
        if (clientId < 1L) {
            throw new IllegalArgumentException("Bad order id");
        }
        return orders.stream()
                .filter(order -> order.getClientId().equals(clientId)).collect(Collectors.toList());
    }

    @Override
    public List<Order> list(String sortKey) {
        List<Order> fetchedOrders = new ArrayList<>(orders);
        sort(fetchedOrders, sortKey);
        return fetchedOrders;
    }

    @Override
    public void sort(List<Order> orders, String orderSortKey) {
        OrderSortKey sortKey;

        if(containsSortParameter(orderSortKey)){
            sortKey = OrderSortKey.valueOf(orderSortKey.toUpperCase());
        }else{
            sortKey = OrderSortKey.NATURAL;
        }

        switch (sortKey) {
            case PRICE -> orders.sort(Comparator.comparing(Order::getPrice));
            case DATE ->
                    orders.sort(Comparator.comparing(Order::getCompletionDate, Comparator.nullsLast(LocalDateTime::compareTo)));
            case STATUS -> orders.sort(Comparator.comparing(Order::getStatus));
        }
    }

    public boolean containsSortParameter(String orderSortKey) {
        for (OrderSortKey sortKey : OrderSortKey.values()) {
            if (sortKey.name().equalsIgnoreCase(orderSortKey)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<Order> findOrdersInPeriodOfCompletionDateWithPositiveStatus(LocalDateTime startCompletionDate, LocalDateTime endCompletionDate) {
        LocalDateTime currentTime = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(59);
        if (startCompletionDate.isAfter(currentTime) || endCompletionDate.isAfter(currentTime)) {
            throw new IllegalArgumentException("Not existed data");
        }
        List<Order> ordersInPeriod = new ArrayList<>();
        for (Order order : orders) {
            if (order.getStatus() != Order.OrderStatus.COMPLETED) {
                continue;
            }
            LocalDateTime orderCompletionDate = order.getCompletionDate();
            if (
                    (orderCompletionDate.isAfter(startCompletionDate) || orderCompletionDate.isEqual(startCompletionDate))
                            && (orderCompletionDate.isBefore(endCompletionDate) || orderCompletionDate.isEqual(endCompletionDate))
                            && order.getStatus() == Order.OrderStatus.COMPLETED
            ) {
                ordersInPeriod.add(order);
            }
        }
        return ordersInPeriod;
    }
}
