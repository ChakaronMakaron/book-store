package com.andersen.controllers;

public interface BookController {

    void list(String sortKey);

    void add(Long id, int amountToAdd);
}
