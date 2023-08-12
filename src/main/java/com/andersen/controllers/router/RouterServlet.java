package com.andersen.controllers.router;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import com.andersen.annotations.Delete;
import com.andersen.annotations.Get;
import com.andersen.annotations.Post;
import com.andersen.annotations.Put;
import com.andersen.controllers.BookController;
import com.andersen.controllers.OrderController;
import com.andersen.controllers.RequestController;

import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Singleton
public class RouterServlet extends HttpServlet {

    private BookController bookController;
    private OrderController orderController;
    private RequestController requestController;

    private List<Method> getHandlers;
    private List<Method> postHandlers;
    private List<Method> putHandlers;
    private List<Method> deleteHandlers;

    @Inject
    public RouterServlet(BookController bookController, OrderController orderController,
            RequestController requestController) {
        this.bookController = bookController;
        this.orderController = orderController;
        this.requestController = requestController;

        this.getHandlers = findHandlersByHttpMethod(Get.class);
        this.postHandlers = findHandlersByHttpMethod(Post.class);
        this.putHandlers = findHandlersByHttpMethod(Put.class);
        this.deleteHandlers = findHandlersByHttpMethod(Delete.class);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            
            getHandlers.stream()
                .filter(handler -> handler.getAnnotation(Get.class).value().equals(req.getRequestURI()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No mapping for GET %s".formatted(req.getRequestURI())))
                .invoke(req, resp);

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            
            postHandlers.stream()
                .filter(handler -> handler.getAnnotation(Post.class).value().equals(req.getRequestURI()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No mapping for POST %s".formatted(req.getRequestURI())))
                .invoke(req, resp);

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            
            deleteHandlers.stream()
                .filter(handler -> handler.getAnnotation(Delete.class).value().equals(req.getRequestURI()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No mapping for DELETE %s".formatted(req.getRequestURI())))
                .invoke(req, resp);

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            
            putHandlers.stream()
                .filter(handler -> handler.getAnnotation(Put.class).value().equals(req.getRequestURI()))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No mapping for PUT %s".formatted(req.getRequestURI())))
                .invoke(req, resp);

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private List<Method> findHandlersByHttpMethod(Class<? extends Annotation> httpMethodAnnotation) {
        return List.of(bookController, orderController, requestController).stream()
                .map(controller -> Arrays.asList(controller.getClass().getDeclaredMethods()))
                .flatMap(list -> list.stream())
                .filter(method -> Objects.nonNull(method.getAnnotation(httpMethodAnnotation)))
                .toList();
    }
}
