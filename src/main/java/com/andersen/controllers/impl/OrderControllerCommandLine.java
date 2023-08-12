package com.andersen.controllers.impl;

import com.andersen.controllers.OrderController;
import com.andersen.enums.OrderSortKey;
import com.andersen.models.Order;
import com.andersen.services.impl.BookServiceImpl;
import com.andersen.services.impl.OrderServiceImpl;
import com.google.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

public class OrderControllerCommandLine implements OrderController {
    @Inject
    private BookServiceImpl bookService;
    @Inject
    private OrderServiceImpl orderService;

    @Override
    public List<Order> getAllSorted(String sortKey) {
        OrderSortKey orderSortKey = OrderSortKey.valueOf(sortKey.toUpperCase());
        return orderService.getAllSorted(orderSortKey);
    }

    @Override
    public void addOrder(Order order) {
        orderService.add(order);
    }

    @Override
    public void complete(Long id) {
        orderService.complete(id);
    }

    @Override
    public int getIncomeBySpecifiedPeriod(LocalDateTime from, LocalDateTime to) {
        return orderService.getIncomeBySpecifiedPeriod(from, to);
    }

}
