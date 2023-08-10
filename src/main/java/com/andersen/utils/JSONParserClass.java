package com.andersen.utils;

import com.andersen.models.Book;
import com.andersen.models.Order;
import com.andersen.models.Request;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Properties;

public class JSONParserClass {

    private static final Gson gson;

    private static final String booksPath;
    private static final String ordersPath;
    private static final String requestsPath;
    private static final Properties properties;
    private static final InputStream input;

    static {
        gson = new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .setPrettyPrinting()
                .create();

        properties = new Properties();

        try {
            input = new FileInputStream("src/main/resources/path.properties");
            properties.load(input);
            booksPath = properties.getProperty("booksFilePath");
            ordersPath = properties.getProperty("ordersFilePath");
            requestsPath = properties.getProperty("requestsFilePath");

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void writeJsonBooks(List<Book> books) {

        try (FileWriter bookWriter = new FileWriter(booksPath)) {
            gson.toJson(books, bookWriter);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void writeJsonOrders(List<Order> orders) {

        try (FileWriter ordersWriter = new FileWriter(ordersPath)) {
            gson.toJson(orders, ordersWriter);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public void writeJsonRequests(List<Request> requests) {

        try (FileWriter bookWriter = new FileWriter(requestsPath)) {
            gson.toJson(requests, bookWriter);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Book> readJsonBooks() {
        List<Book> books;

        try (FileReader booksReader = new FileReader(booksPath)) {
            Type bookListType = new TypeToken<List<Book>>() {
            }.getType();
            books = gson.fromJson(booksReader, bookListType);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return books;
    }

    public List<Order> readJsonOrders() {
        List<Order> orders;

        try (FileReader ordersReader = new FileReader(ordersPath)) {
            Type orderListType = new TypeToken<List<Order>>() {
            }.getType();
            orders = gson.fromJson(ordersReader, orderListType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return orders;
    }

    public List<Request> readJsonRequests() {
        List<Request> requests;

        try (FileReader booksReader = new FileReader(requestsPath)) {
            Type bookListType = new TypeToken<List<Request>>() {
            }.getType();
            requests = gson.fromJson(booksReader, bookListType);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return requests;
    }
}
