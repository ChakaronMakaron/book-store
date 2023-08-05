package com.andersen.repositories;

import com.andersen.models.Order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    public Order findById(Long bookId) {
        if(bookId < 1L){
            throw new IllegalArgumentException("Bad order id");
        }
        for(Order order : orders){
            if(order.getId().equals(bookId)){
                return order;
            }
        }
        return null;
    }

    public void remove(Long bookId) {
        for(Order order : orders){
            if(order.getId().equals(bookId)){
                orders.remove(order);
            }
        }
    }

    public List<Order> findOrdersByClientId(Long clientId){
        if(clientId < 1L){
            throw new IllegalArgumentException("Bad order id");
        }
        List<Order> ordersOfClient = new ArrayList<>();
        for(Order order : orders){
            if(order.getClientId().equals(clientId)){
                ordersOfClient.add(order);
            }
        }
        return ordersOfClient;
    }

    public List<Order> findOrdersInPeriodOfCompletionDateWithPositiveStatus(LocalDateTime startCompletionDate, LocalDateTime endCompletionDate){
        LocalDateTime currentTime = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(59);
        if(startCompletionDate.isAfter(currentTime) || endCompletionDate.isAfter(currentTime)){
            throw new IllegalArgumentException("Not existed data");
        }
        List<Order> ordersInPeriod = new ArrayList<>();
        for(Order order : orders){
            if (order.getStatus()!= Order.OrderStatus.COMPLETED){
                continue;
            }
            LocalDateTime orderCompletionDate = order.getCompletionDate();
            if(
                    (orderCompletionDate.isAfter(startCompletionDate) || orderCompletionDate.isEqual(startCompletionDate))
                    && (orderCompletionDate.isBefore(endCompletionDate) || orderCompletionDate.isEqual(endCompletionDate))
                            && order.getStatus() == Order.OrderStatus.COMPLETED
            ){
                ordersInPeriod.add(order);
            }
        }
        return ordersInPeriod;
    }
}
