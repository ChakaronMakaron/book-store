package com.andersen.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Order {

    private Long id;
    private Long clientId;
    private LocalDateTime completionDate;
    private OrderStatus status;
    private List<Request> requests;
    private Integer price;

    public static enum OrderStatus {
        IN_PROCESS, COMPLETED, CANCELED
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

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return id
                + ". clientId = " + clientId
                + (completionDate == null ? ", " : ", completionDate = " + completionDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) + ", ")
                + "price = " + price + ", "
                + "status = " + status;

    }
}
