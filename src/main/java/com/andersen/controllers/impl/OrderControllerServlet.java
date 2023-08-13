package com.andersen.controllers.impl;

import com.andersen.controllers.OrderController;
import com.andersen.enums.OrderSortKey;
import com.andersen.models.Order;
import com.andersen.services.impl.BookServiceImpl;
import com.andersen.services.impl.OrderServiceImpl;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.time.LocalDateTime;
import java.util.List;

@Singleton
public class OrderControllerServlet implements OrderController {
    private final BookServiceImpl bookService;
    private final OrderServiceImpl orderService;

    @Inject
    public OrderControllerServlet(BookServiceImpl bookService, OrderServiceImpl orderService) {
        this.bookService = bookService;
        this.orderService = orderService;
    }

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
