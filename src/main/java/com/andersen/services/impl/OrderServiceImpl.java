package com.andersen.services.impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.andersen.authorization.Authenticator;
import com.andersen.models.Book;
import com.andersen.models.Order;
import com.andersen.models.Request;
import com.andersen.repositories.OrderRepository;
import com.andersen.services.OrderService;

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
    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    @Override
    public void add(Order order) {
        orderRepository.add(order);
    }

    @Override
    public void save(Order order) {
        Order orderFromRepo = orderRepository.findById(order.getId());

        orderFromRepo.setStatus(order.getStatus());
        orderFromRepo.setCompletionDate(order.getCompletionDate());
    }

    @Override
    public List<Order> getAllClientOrders(Long clientId) {
        return orderRepository.findOrdersByClientId(clientId);
    }

    public void processOrder(Order order) {
        List<Request> requestsFromOrder = order.getRequests();
        Iterator<Request> iterator = requestsFromOrder.iterator();

        while (iterator.hasNext()) {
            Request request = iterator.next();
            Book book = request.getBook();
            if(order.getPrice() == null){
                order.setPrice(book.getPrice() * request.getAmount());
            }else{
                order.setPrice(order.getPrice() + book.getPrice() * request.getAmount());
            }

            if (request.getAmount() > book.getAmount()) {
                requestService.add(request);
            } else {
                bookService.changeAmountOfBook(book.getId(), book.getAmount() - request.getAmount());
                iterator.remove();
            }
        }

        if(order.getRequests().isEmpty()){
            order.setStatus(Order.OrderStatus.COMPLETED);
            order.setCompletionDate(LocalDateTime.now());
        }
    }

    public void processUserInput(Order order, String bookRequest) {
        String[] inputValues = bookRequest.split(" "); // get inputValues from input

        if (inputValues.length != 2){
            throw new IllegalArgumentException("Bad input");
        }
        long bookId;
        Integer amount;
        try {
            bookId = Long.parseLong(inputValues[0].trim());
            amount = Integer.parseInt(inputValues[1].trim());
        }catch (IllegalArgumentException e){
            throw new IllegalArgumentException("Wrong input parameters");
        }

        Book book = bookService.getBookById(bookId);

        if(book != null){ // if book was found -> create order or change order
            if(order.getId() != null){ // if order already exists -> set new request for the book
                addRequestToOrder(order, amount, book);
            }else{ // if order isn't exist -> create it and set first request
                createOrder(order, amount, book);
            }
        }
    }

    private void createOrder(Order order, Integer amount, Book book) {
        order.setId((long) getAll().size() + 1);
        order.setClientId(Authenticator.getInstance().getUser().getId());
        order.setStatus(Order.OrderStatus.IN_PROCESS);
        addRequestToOrder(order, amount, book);
    }

    private void addRequestToOrder(Order order, Integer amount, Book book) {
        if(order.getRequests() == null){
            order.setRequests(new ArrayList<>());
        }
        List<Request> requests = order.getRequests();
        requests.add(new Request((long) requests.size() + 1, book, amount));

        order.setRequests(requests);
    }
}
