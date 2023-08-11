package com.andersen.controllers.router;

import com.andersen.controllers.BookController;
import jakarta.inject.Singleton;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Singleton
public class RouterServlet extends HttpServlet {
    private final BookController bookController;

    public RouterServlet(BookController bookController) {
        this.bookController = bookController;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        bookController.list("natural");
        bookController.bookSupply(1L, 10);
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
