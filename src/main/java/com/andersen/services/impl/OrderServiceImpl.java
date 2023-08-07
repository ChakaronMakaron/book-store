package com.andersen.services.impl;

import com.andersen.authorization.Authenticator;
import com.andersen.enums.OrderSortKey;
import com.andersen.models.Book;
import com.andersen.models.Order;
import com.andersen.models.Request;
import com.andersen.repositories.OrderRepository;
import com.andersen.services.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RequestServiceImpl requestService;
    private final BookServiceImpl bookService;

    public OrderServiceImpl(OrderRepository orderRepository, RequestServiceImpl requestService, BookServiceImpl bookService) {
        this.orderRepository = orderRepository;
        this.requestService = requestService;
        this.bookService = bookService;
    }

    @Override
    public List<Order> list(OrderSortKey sortKey) {
        return orderRepository.list(sortKey);
    }

    @Override
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public void add(Order order) {
        orderRepository.add(order);
    }

    @Override
    public void save(Order order) {
        Optional<Order> orderFromRepo = orderRepository.findByOrderId(order.getId());

        orderFromRepo.ifPresent(existingOrder -> {
            existingOrder.setStatus(order.getStatus());
            existingOrder.setCompletionDate(order.getCompletionDate());
        });
    }

    @Override
    public List<Order> getAllClientOrders(Long clientId, OrderSortKey sortKey) {
        return orderRepository.findOrdersByClientId(clientId, sortKey);
    }

    @Override
    public void changeStatus(Long orderId, Order.OrderStatus newStatus) {
        orderRepository.findByOrderId(orderId).ifPresent(order -> {
            order.setStatus(newStatus);
        });
    }

    public List<Order> getOrdersFilteredInPeriod(LocalDateTime startCompletionDate, LocalDateTime endCompletionDate) {
        return orderRepository.findOrdersInPeriodOfCompletionDateWithPositiveStatus(startCompletionDate, endCompletionDate);
    }

    public void processOrder(Order order) {
        List<Request> requestsFromOrder = order.getRequests();
        Iterator<Request> iterator = requestsFromOrder.iterator();

        while (iterator.hasNext()) {
            Request request = iterator.next();
            Book book = request.getBook();
            if (order.getPrice() == null) {
                order.setPrice(book.getPrice() * request.getAmount());
            } else {
                order.setPrice(order.getPrice() + book.getPrice() * request.getAmount());
            }

            if (request.getAmount() > book.getAmount()) {
                requestService.add(request);
            } else {
                bookService.changeAmountOfBook(book.getId(), book.getAmount() - request.getAmount());
                iterator.remove();
            }
        }

        if (order.getRequests().isEmpty()) {
            order.setStatus(Order.OrderStatus.COMPLETED);
            order.setCompletionDate(LocalDateTime.now());
        }
    }

    public void processUserInput(Order order, String bookRequest) {
        String[] inputValues = bookRequest.split(" "); // get inputValues from input

        if (inputValues.length != 2) {
            throw new IllegalArgumentException("Bad input");
        }
        long bookId;
        Integer amount;
        try {
            bookId = Long.parseLong(inputValues[0].trim());
            amount = Integer.parseInt(inputValues[1].trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Wrong input parameters");
        }

        Optional<Book> book = bookService.getBookById(bookId);

        book.ifPresent(theBook -> {                          // if book was found -> create order or change order
            if (order.getId() != null) {                       // if order already exists -> set new request for the book
                addRequestToOrder(order, amount, theBook);
            } else {                                           // if order isn't exist -> create it and set first request
                createOrder(order, amount, theBook);
            }
        });
    }

    private void createOrder(Order order, Integer amount, Book book) {
        order.setId((long) getAll().size() + 1);
        order.setClientId(Authenticator.getInstance().getUser().getId());
        order.setStatus(Order.OrderStatus.IN_PROCESS);
        addRequestToOrder(order, amount, book);
    }

    private void addRequestToOrder(Order order, Integer amount, Book book) {
        if (order.getRequests() == null) {
            order.setRequests(new ArrayList<>());
        }
        List<Request> requests = order.getRequests();
        requests.add(new Request((long) requests.size() + 1, book, amount));

        order.setRequests(requests);
    }
}
