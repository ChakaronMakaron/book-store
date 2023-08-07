package com.andersen.models;

public class Request {

    private Long id;
    private Long clientId;

    private Book book;

    private Integer amount;

    public Request(Long id, Long clientId, Book book, Integer amount) {
        this.id = id;
        this.clientId = clientId;
        this.book = book;
        this.amount = amount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return id
                + ". book = " + book.getName()
                + ", price = " + book.getPrice()
                + ", bookAmount = " + book.getAmount()
                + ", requestAmount = " + amount;
    }
}
