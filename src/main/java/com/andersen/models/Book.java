package com.andersen.models;

public class Book {

    private BookStatus status;
    private Long id;
    private String name;
    private Integer price;
    private Integer amount;

    public enum BookStatus {
        IN_STOCK, OUT_OF_STOCK
    }


    public Book(Long id, String name, Integer price, Integer amount) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        this.status = (amount >= 1) ? BookStatus.IN_STOCK : BookStatus.OUT_OF_STOCK;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
        this.status = (amount >= 1) ? BookStatus.IN_STOCK : BookStatus.OUT_OF_STOCK;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return id + ". name = " + name + ", price = " + price + ", amount = " + amount + ", status = " + status;
    }


}
