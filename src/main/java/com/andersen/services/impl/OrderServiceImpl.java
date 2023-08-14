package com.andersen.services.impl;

import com.andersen.authorization.Authenticator;
import com.andersen.models.Book;
import com.andersen.models.Order;
import com.andersen.models.Request;
import com.andersen.repositories.OrderRepository;
import com.andersen.services.OrderService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final RequestServiceImpl requestService;
    private final BookServiceImpl bookService;
    private boolean RequestCreationAvailabilityInOrder = true;


    public OrderServiceImpl(OrderRepository orderRepository, RequestServiceImpl requestService, BookServiceImpl bookService) {
        this.orderRepository = orderRepository;
        this.requestService = requestService;
        this.bookService = bookService;
    }

    @Override
    public List<Order> list(String sortKey) {
        List<Order> orders = orderRepository.findAll();
        orderRepository.sort(orders, sortKey);
        return orders;
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
    public List<Order> getAllClientOrders(Long clientId, String sortKey) {
        List<Order> clientOrders = orderRepository.findOrdersByClientId(clientId);
        orderRepository.sort(clientOrders, sortKey);
        return clientOrders;
    }

    @Override
    public void changeStatus(Long orderId, Order.OrderStatus newStatus) {
        orderRepository.findByOrderId(orderId).ifPresent(order -> {
            order.setStatus(newStatus);

            changeRequestsStatusByOrderStatus(newStatus, order);
        });
    }

    @Override
    public void changeRequestsStatusByOrderStatus(Order.OrderStatus newStatus, Order order) {
        Request.RequestStatus requestStatus = Request.RequestStatus.valueOf(newStatus.toString());
        switch (requestStatus) {

            case COMPLETED -> order
                    .getRequests()
                    .stream()
                    .filter(request -> request
                            .getRequestStatus() != Request.RequestStatus.COMPLETED)
                    .forEach(request -> request
                            .setRequestStatus(Request.RequestStatus.INTERRUPTED));

            case CANCELED ->
                    order.getRequests().forEach(request -> request.setRequestStatus(Request.RequestStatus.CANCELED));
        }
    }

    @Override
    public List<Order> getOrdersFilteredInPeriod(LocalDateTime startCompletionDate, LocalDateTime endCompletionDate) {
        return orderRepository.findOrdersInPeriodOfCompletionDateWithPositiveStatus(startCompletionDate, endCompletionDate);
    }

    @Override
    public void setRequestCreationAvailabilityInOrder(boolean choice) {
        this.RequestCreationAvailabilityInOrder = choice;
    }

    public void processOrder(Order order) {
        List<Request> requestsFromOrder = order.getRequests();


        for (Request request : requestsFromOrder) {
            Book book = request.getBook();

            setOrderPrice(order, request.getAmount(), book.getPrice());

            if (request.getAmount() > book.getAmount()) {
                //
                requestService.add(request);
            } else {
                bookService.changeAmountOfBook(book.getId(), book.getAmount() - request.getAmount());
                request.setRequestStatus(Request.RequestStatus.COMPLETED);
            }
        }

        boolean areAllRequestsCompleted = order.getRequests()
                .stream()
                .noneMatch(request -> request.getRequestStatus()
                        .equals(Request.RequestStatus.IN_PROCESS));

        if (areAllRequestsCompleted) {
            order.setStatus(Order.OrderStatus.COMPLETED);
            order.setCompletionDate(LocalDateTime.now());
        }
    }

    private void setOrderPrice(Order order, Integer requestAmount, Integer bookPrice) {
        if (order.getPrice() == null) {
            order.setPrice(bookPrice * requestAmount);
        } else {
            order.setPrice(order.getPrice() + bookPrice * requestAmount);
        }
    }

    @Override
    public void processUserInput(Order order, String bookRequest) {
        String[] inputValues = bookRequest.split(" "); // get inputValues from input
        long bookId;
        Integer amount;

        if (inputValues.length != 2) {
            throw new IllegalArgumentException("Bad input");
        }

        try {
            bookId = Long.parseLong(inputValues[0].trim());
            amount = Integer.parseInt(inputValues[1].trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Wrong input parameters");
        }

        Optional<Book> book = bookService.getBookById(bookId);
        book.ifPresent(theBook -> {                          // if book was found -> create order or change order
            if (order.getId() != null) {                       // if order already exists -> set new request for the book
                if (amount > theBook.getAmount()) {
                    if (RequestCreationAvailabilityInOrder) {
                        addRequestToOrder(order, amount, theBook);
                    } else {
                        System.out.println("SWITCH OFFED for processUserInput");
                    }
                } else {
                    addRequestToOrder(order, amount, theBook);
                }

            } else {                                           // if order isn't exist -> create it and set first request
                createOrder(order, amount, theBook);
            }
        });
    }

    private void createOrder(Order order, Integer amount, Book book) {
            order.setId((long) orderRepository.findAll().size() + 1);
            order.setClientId(Authenticator.getInstance().getUser().getId());
            order.setStatus(Order.OrderStatus.IN_PROCESS);

        if (book.getAmount() < amount && !RequestCreationAvailabilityInOrder) {
            System.out.println("Requests switched off for createOrder");
        } else {
                addRequestToOrder(order, amount, book);
        }
    }

    private void addRequestToOrder(Order order, Integer amount, Book book) {
        if (order.getRequests().isEmpty()) {
            order.setRequests(new ArrayList<>());
        }
        List<Request> requests = order.getRequests();
        requests.add(new Request((long) requests.size() + 1, order.getClientId(), book, amount, Request.RequestStatus.IN_PROCESS));
        order.setRequests(requests);
    }
}
