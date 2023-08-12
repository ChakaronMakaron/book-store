package com.andersen.controllers.router;

import com.andersen.controllers.BookController;
import com.google.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@Singleton
public class RouterServlet extends HttpServlet {
    @Inject
    private BookController bookController;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(bookController.getAllSorted("price"));
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
