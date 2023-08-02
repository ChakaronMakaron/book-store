package com.andersen.services;

import com.andersen.models.Order;

import java.util.List;

public interface OrderService {
    public List<Order> getAll();
    public void add(Order order);
}
