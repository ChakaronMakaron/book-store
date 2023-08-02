package com.andersen.models;

import java.time.LocalDateTime;
import java.util.List;

public class Order {

    private Long id;
    private Long clientId;
    private LocalDateTime completionDate;
    private OrderStatus status;
    private List<Request> requests;

    public static enum OrderStatus {
        IN_PROCESS, COMPLETED
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

    @Override
    public String toString() {
        return "Order [id=" + id + ", clientId=" + clientId + ", completionDate=" + completionDate
                + ", status=" + status + ", requests=" + requests + "]";
    }
}
