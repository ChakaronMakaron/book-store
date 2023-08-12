package com.andersen.services.impl;

import com.andersen.enums.BookSortKey;
import com.andersen.models.Book;
import com.andersen.models.Order;
import com.andersen.models.Request;
import com.andersen.repositories.BookRepository;
import com.andersen.repositories.OrderRepository;
import com.andersen.repositories.RequestRepository;
import com.andersen.services.BookService;
import com.google.inject.Inject;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class BookServiceImpl implements BookService {

    @Inject
    private BookRepository bookRepository;
    @Inject
    private OrderRepository orderRepository;
    @Inject
    private RequestRepository requestRepository;

    @Override
    public List<Book> getAllSorted(BookSortKey sortKey) {
        return bookRepository.getAllSorted(sortKey);
    }

    @Override
    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public void changeBookStatus(Long id, Book.BookStatus status) {
        bookRepository.changeBookStatus(id, status);

        if (status == Book.BookStatus.IN_STOCK) {
            completeRequests(id);
        }
    }

    public void completeRequests(Long bookId) {
        List<Request> requests = requestRepository.getAll();

        requests.forEach(request -> {
            if (Objects.equals(request.getBook().getId(), bookId)) {
                requestRepository.changeRequestStatus(request.getId(), Request.RequestStatus.COMPLETED);
            }
        });

        completeOrders();
    }

    public void completeOrders() {
        List<Order> orders = orderRepository.getAll();

        for (Order order : orders) {
            completeOrder(order);
        }
    }

    public void completeOrder(Order order) {
        for (Request request : order.getRequests()) {
            if (request.getRequestStatus() == Request.RequestStatus.IN_PROCESS) {
                return;
            }
        }
        order.setStatus(Order.OrderStatus.COMPLETED);
    }
}
