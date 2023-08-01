package com.andersen.controllers.impl;

import java.util.List;

import com.andersen.controllers.OrderController;
import com.andersen.models.BookOrder;

public class OrderControllerImpl implements OrderController {

    @Override
    public void list(String sortKey) {
        System.out.println("order list");
    }

    @Override
    public void complete(Long orderId) {
        System.out.println("order complete");
    }

    @Override
    public void create(List<BookOrder> books) {
        System.out.println("order create");
    }

    @Override
    public void cancel(Long orderId) {
        System.out.println("order cancel");
    }
}
