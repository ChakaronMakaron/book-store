package com.andersen.controllers.impl;

import com.andersen.authorization.Authenticator;
import com.andersen.controllers.OrderController;
import com.andersen.models.Book;
import com.andersen.models.Order;
import com.andersen.models.Request;
import com.andersen.services.impl.BookServiceImpl;
import com.andersen.services.impl.OrderServiceImpl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.*;

public class OrderControllerCommandLine implements OrderController {

    private final BookServiceImpl bookService;
    private final OrderServiceImpl orderService;
    private final Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);

    public OrderControllerCommandLine(BookServiceImpl bookService, OrderServiceImpl orderService) {
        this.bookService = bookService;
        this.orderService = orderService;
    }

    @Override
    public void list(String sortKey) {
        Long clientId = Authenticator.getUser().getId();
        List<Order> orders = orderService.getAllClientOrders(clientId);

        if (sortKey != null) {
            switch (sortKey) {
                case "price": {
                    orders.sort(Comparator.comparing(Order::getPrice));
                }
                case "date": {
                    orders.sort(Comparator.comparing(Order::getCompletionDate, Comparator.nullsLast(LocalDateTime::compareTo)));
                    break;
                }
                case "status": {
                    orders.sort(Comparator.comparing(Order::getStatus));
                    break;
                }
                default: {
                    throw new IllegalArgumentException("Wrong sort key");
                }
            }
        }
        for(Order order : orders){
            System.out.println(order.toString());

            if(order.getRequests().size() > 0){
                List<Request> requests = order.getRequests();
                System.out.println("Order requests:");

                for(Request request : requests){
                    System.out.println("\t" + request.toString());
                }
            }
        }

    }

    @Override
    public void complete(Long orderId) {
        System.out.println("order complete");
    }

    @Override
    public void create() {
        Order order = new Order();

        List<Book> books = bookService.getAll(); // find all books and print it to console
        for(Book book : books){
            System.out.println(book);
        }

        System.out.println("\nPrint bookId and amount like this: \"1 2\"");
        System.out.println("Type \"finish\" to complete creating the order");

        while (true){
            System.out.print(">>>");
            String bookRequest = sc.nextLine();

            if(bookRequest.trim().equals("finish")){ // command to finish creating the order
                orderService.add(order); // final save order
                break;
            }
            orderService.processUserInput(order, bookRequest); // creating order and requests
        }
        orderService.processOrder(order); // checking all requests in the order

    }

    @Override
    public void cancel(Long orderId) {
        System.out.println("order cancel");
    }

}
