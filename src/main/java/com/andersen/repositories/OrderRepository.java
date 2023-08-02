package com.andersen.repositories;

import com.andersen.models.Order;

import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    List<Order> orders = new ArrayList<>();

    public List<Order> findAll(){
        return orders;
    }

    public void add(Order order){
        if(order == null){
            throw new IllegalArgumentException("Order is null");
        }
        orders.add(order);
    }
}
