package com.andersen.controllers.impl;

import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.andersen.controllers.OrderController;
import com.andersen.models.Book;
import com.andersen.models.Order;
import com.andersen.services.BookService;
import com.andersen.services.OrderService;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Singleton
public class ServletOrderController implements OrderController {

    private final BookService bookService;
    private final OrderService orderService;
    private final Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);

    @Inject
    public ServletOrderController(BookService bookService, OrderService orderService) {
        this.bookService = bookService;
        this.orderService = orderService;
    }

    @Override
    public List<Order> list(HttpServletRequest request, HttpServletResponse response) {
        /*
        // Long clientId = Authenticator.getInstance().getUser().getId();

        Long clientId = 1L; // TODO

        List<Order> orders = orderService.getAllClientOrders(clientId, sortKey);

        orders.forEach(order -> {
            System.out.println(order);
            System.out.println("Order requests:");
            order.getRequests().forEach(request -> System.out.printf("\t%s%n", request));
        });
        return orders;
        */
        return Collections.emptyList();
    }

    @Override
    public void complete(HttpServletRequest request, HttpServletResponse response) {
        /*
        if (orderId < 1L) {
            throw new IllegalArgumentException("Wrong order id");
        }
        orderService.changeStatus(orderId, Order.OrderStatus.COMPLETED);
         */
    }

    @Override
    public void create(HttpServletRequest request, HttpServletResponse response) {
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
    public void cancel(HttpServletRequest request, HttpServletResponse response) {
        /*
        if (orderId < 1L) {
            throw new IllegalArgumentException("Wrong order id");
        }
        orderService.changeStatus(orderId, Order.OrderStatus.CANCELED);
        */
    }

    @Override
    public void countIncome(HttpServletRequest request, HttpServletResponse response) {

        /*
        List<Order> orders = orderService.getOrdersFilteredInPeriod(startPeriodCompletionDate, endPeriodOfCompletionDate);
        int incomeCounter = 0;
        for (Order order : orders) {
            incomeCounter += order.getPrice();
            System.out.println(order);
        }
        System.out.printf("Total income for period from %s to %s is %s", startPeriodCompletionDate,
                endPeriodOfCompletionDate, incomeCounter);
        */
    }
}
