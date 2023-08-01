package com.andersen.controllers.impl;

import com.andersen.controllers.OrderController;
import com.andersen.models.BookOrder;

import java.util.List;

public class OrderControllerImpl implements OrderController {

    @Override
    public void list(Long clientId, String sortKey) {
        System.out.println("order list");
    }

    @Override
    public void complete(Long orderId) {
        System.out.println("order complete");
    }

    @Override
    public void create(Long clientId, List<BookOrder> books) {
        System.out.println("order create");
    }

    @Override
    public void cancel(Long orderId) {
        System.out.println("order cancel");
    }
}
