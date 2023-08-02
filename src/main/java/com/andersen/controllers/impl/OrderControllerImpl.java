package com.andersen.controllers.impl;

import com.andersen.authorization.Authenticator;
import com.andersen.controllers.OrderController;
import com.andersen.models.Book;
import com.andersen.models.Order;
import com.andersen.models.Request;
import com.andersen.services.impl.BookServiceImpl;
import com.andersen.services.impl.OrderServiceImpl;
import com.andersen.services.impl.RequestServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.IntStream;

public class OrderControllerImpl implements OrderController {

    private final BookServiceImpl bookService;
    private final OrderServiceImpl orderService;
    private final RequestServiceImpl requestService;
    private final Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);

    public OrderControllerImpl(BookServiceImpl bookService, OrderServiceImpl orderService, RequestServiceImpl requestService) {
        this.bookService = bookService;
        this.orderService = orderService;
        this.requestService = requestService;
    }

    @Override
    public void list(String sortKey) {
        System.out.println("order list");
    }

    @Override
    public void complete(Long orderId) {
        System.out.println("order complete");
    }

    @Override
    public void create() {

        boolean execute = true;
        Order order = new Order();

        List<Book> books = bookService.getAll(); // find all books and print it to console
        for(Book book : books){
            System.out.println(book);
        }

        System.out.println("\nPrint bookId and amount like this: \"1 2\"");
        System.out.println("Type \"finish\" to complete creating the order");
        while (execute){
            System.out.print(">>>");
            String bookRequest = sc.nextLine();

            if(bookRequest.trim().equals("finish")){ // command to finish creating the order
                orderService.add(order); // final save order
                execute = false;
                continue;
            }

            String[] args = bookRequest.split(" "); // get args from input
            if (args.length != 2) throw new IllegalArgumentException("Bad input"); // TODO: need to add check if it's nums
            IntStream.range(0, args.length).forEach(i -> args[i] = args[i].trim()); // trim() all args

            Long bookId = Long.parseLong(args[0]);
            Integer amount = Integer.parseInt(args[1]);
            Book book = bookService.getBookById(bookId);

            if(book != null){ // if book was found -> create order or change order
                if(order.getId() != null){ // if order already exists -> set new request for the book
                    setRequestsToOrder(order, amount, book);
                }else{ // if order isn't exist -> create it and set first request
                    order.setId((long) orderService.getAll().size() + 1);
                    order.setClientId(Authenticator.getUser().getId());
                    order.setStatus(Order.OrderStatus.IN_PROCESS);
                    setRequestsToOrder(order, amount, book);
                }
            }
            // TODO: make a method to check requests in order
        }
    }

    @Override
    public void cancel(Long orderId) {
        System.out.println("order cancel");
    }

    private void setRequestsToOrder(Order order, Integer amount, Book book) { // a method to avoid code duplication of changing requests in the order
        if(order.getRequests() == null){
            order.setRequests(new ArrayList<>());
        }
        List<Request> requests = order.getRequests();
        requests.add(new Request((long) requestService.getAll().size() + 1, book, amount));

        order.setRequests(requests);
    }
}
