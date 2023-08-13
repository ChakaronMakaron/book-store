package com.andersen.controllers.router;

import com.andersen.controllers.BookController;
import com.andersen.controllers.OrderController;
import com.andersen.controllers.RequestController;
import com.andersen.models.Book;
import com.andersen.models.Order;
import com.andersen.models.Request;
import com.google.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Singleton
public class RouterServlet extends HttpServlet {
    @Inject
    private BookController bookController;
    @Inject
    private OrderController orderController;
    @Inject
    private RequestController requestController;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
/*        System.out.println(bookController.getAllSorted("price"));
        List<Book> books = bookController.getAllSorted("price");*/
        List<Book> books = bookController.getAll();
//        orderController.addOrder(new Order(1L, 1L, List.of(
//                new Request(1L, 1L, books.get(0), 10),
//                new Request(2L, 1L, books.get(1), 1)
//        )));
       bookController.changeBookStatus(books.get(1).getId(), Book.BookStatus.IN_STOCK);
//        orderController.complete(1L);
        System.out.println("DO GET");
        resp.getOutputStream().println("GET Privet");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DO POST");
        resp.getOutputStream().println("POST Privet");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DO DELETE");
        resp.getOutputStream().println("DELETE Privet");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("DO PUT");
        resp.getOutputStream().println("PUT Privet");
    }
}
