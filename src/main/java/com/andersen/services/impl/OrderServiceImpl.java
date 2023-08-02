package com.andersen.services.impl;

import com.andersen.models.Order;
import com.andersen.repositories.OrderRepository;
import com.andersen.services.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public void add(Order order) {
        orderRepository.add(order);
    }
}
