package com.andersen.services.impl;

import com.andersen.enums.OrderSortKey;
import com.andersen.models.Book;
import com.andersen.models.Order;
import com.andersen.models.Request;
import com.andersen.repositories.BookRepository;
import com.andersen.repositories.OrderRepository;
import com.andersen.repositories.RequestRepository;
import com.andersen.services.OrderService;
import com.google.inject.Inject;

import java.time.LocalDateTime;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    @Inject
    private OrderRepository orderRepository;
    @Inject
    private RequestRepository requestRepository;
    @Inject
    private BookRepository bookRepository;

    @Override
    public List<Order> getAllSorted(OrderSortKey sortKey) {
        return orderRepository.getAllSorted(sortKey);
    }

    @Override
    public void add(Order order) {
        orderRepository.save(order);
    }

    @Override
    public void complete(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Wrong order id"));

        int completedRequests = completeRequests(order);

        if (completedRequests == order.getRequests().size()) {
            orderRepository.changeOrderStatus(order.getId(), Order.OrderStatus.COMPLETED);
        }
    }

    @Override
    public void cancel(Long id) {
        orderRepository.remove(id);
    }

    @Override
    public int getIncomeBySpecifiedPeriod(LocalDateTime from, LocalDateTime to) {
        Integer income = 0;

        List<Order> orders = orderRepository.getAll();

        for (Order order : orders) {
            if (order.getStatus() == Order.OrderStatus.COMPLETED && order.getCompletionDate().isAfter(from) &&
                    order.getCompletionDate().isBefore(to)) {
                income += order.getPrice();
            }
        }

        return income;
    }

    public int completeRequests(Order order) {
        int completedRequests = 0;

        for (Request request : order.getRequests()) {
            if (request.getBook().getStatus() == Book.BookStatus.IN_STOCK) {
                requestRepository.changeRequestStatus(request.getId(), Request.RequestStatus.COMPLETED);
                completedRequests++;
            }
        }

        return completedRequests;
    }
}
