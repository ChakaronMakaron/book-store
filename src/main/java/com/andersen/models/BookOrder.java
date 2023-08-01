package com.andersen.models;

public class BookOrder {

    private String book;
    private int amount;

    public BookOrder(String book, int amount) {
        this.book = book;
        this.amount = amount;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "BookOrder [book=" + book + ", amount=" + amount + "]";
    }
}
