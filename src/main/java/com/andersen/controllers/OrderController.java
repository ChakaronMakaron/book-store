package com.andersen.controllers;

import java.util.List;

import com.andersen.models.BookOrder;

public interface OrderController {
    
    void list(String sortKey);
    void complete(Long orderId);
    void create(List<BookOrder> books);
    void cancel(Long orderId);
}
