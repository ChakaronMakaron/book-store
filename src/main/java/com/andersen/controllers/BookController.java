package com.andersen.controllers;

public interface BookController {

    void list(String sortKey);

    void add(String name, int amountToAdd, int priceToAdd);


}
