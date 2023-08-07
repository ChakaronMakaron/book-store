package com.andersen.models;

public class Request {

    private Long id;
    private Book book;
    private Integer amount;

    private RequestStatus requestStatus;

    public enum RequestStatus {
        IN_PROCESS, COMPLETED, INTERRUPTED, CANCELED
    }

    public Request(Long id, Book book, Integer amount, RequestStatus requestStatus) {
        this.id = id;
        this.book = book;
        this.amount = amount;
        this.requestStatus = requestStatus;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
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
