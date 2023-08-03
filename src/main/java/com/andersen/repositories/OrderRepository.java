package com.andersen.repositories;

import com.andersen.models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class OrderRepository {
    private final List<Order> orders = new ArrayList<>();

    public List<Order> findAll(){
        return orders;
    }

    public void add(Order order){
        if(order == null){
            throw new IllegalArgumentException("Order is null");
        }
        orders.add(order);
    }

    public Optional<Order> findById(Long bookId) {
        if(bookId < 1L){
            throw new IllegalArgumentException("Bad order id");
        }
        return orders.stream()
                .filter(order -> order.getId().equals(bookId))
                .findFirst();
    }

    public List<Order> findOrdersByClientId(Long clientId){
        if(clientId < 1L){
            throw new IllegalArgumentException("Bad order id");
        }
        return orders.stream()
                .filter(order -> order.getClientId().equals(clientId))
                .collect(Collectors.toList());
    }
}
