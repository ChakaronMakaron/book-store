package com.andersen.controllers;

public interface OrderController {
    
    void list(String sortKey);
    void complete(Long orderId);
    void create();
    void cancel(Long orderId);
}
