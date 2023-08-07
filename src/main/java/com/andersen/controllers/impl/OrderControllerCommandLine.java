package com.andersen.controllers.impl;

import com.andersen.authorization.Authenticator;
import com.andersen.controllers.OrderController;
import com.andersen.enums.OrderSortKey;
import com.andersen.models.Book;
import com.andersen.models.Order;
import com.andersen.services.impl.BookServiceImpl;
import com.andersen.services.impl.OrderServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

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
        Long clientId = Authenticator.getInstance().getUser().getId();

        List<Order> orders = orderService.getAllClientOrders(clientId);

        orderService.sort(orders, sortKey);

        orders.forEach(order -> {
            System.out.println(order);
            System.out.println("Order requests:");
            order.getRequests().forEach(request -> {
                System.out.printf("\t%s%n", request);
            });
        });

    }

    @Override
    public void complete(Long orderId) {
        if (orderId < 1L) {
            throw new IllegalArgumentException("Wrong order id");
        }
        orderService.changeStatus(orderId, Order.OrderStatus.COMPLETED);
    }

    @Override
    public void create() {
        Order order = new Order();

        List<Book> books = bookService.getAll(); // find all books and print it to console
        for (Book book : books) {
            System.out.println(book);
        }

        System.out.println("\nPrint bookId and amount like this: \"1 2\"");
        System.out.println("Type \"finish\" to complete creating the order");

        while (true) {
            System.out.print(">>> ");
            String bookRequest = sc.nextLine();

            if (bookRequest.trim().equals("finish")) { // command to finish creating the order
                orderService.add(order); // final save order
                break;
            }
            orderService.processUserInput(order, bookRequest); // creating order and requests
        }
        orderService.processOrder(order); // checking all requests in the order

    }

    @Override
    public void cancel(Long orderId) {
        if (orderId < 1L) {
            throw new IllegalArgumentException("Wrong order id");
        }
        orderService.changeStatus(orderId, Order.OrderStatus.CANCELED);
    }

}
