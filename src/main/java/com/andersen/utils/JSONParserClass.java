package com.andersen.utils;

import com.andersen.controllers.BookController;
import com.andersen.controllers.OrderController;
import com.andersen.models.Book;
import com.andersen.models.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

public class JSONParserClass {

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();
    public void writeJson(OrderController orderController, BookController bookController){
        List<Order> orders = orderController.list("natural");
        List<Book> books = bookController.list("natural");


        try(FileWriter ordersWriter = new FileWriter("src/main/resources/data/orders.json");
            FileWriter bookWriter = new FileWriter("src/main/resources/data/books.json")){
            gson.toJson(orders, ordersWriter);
            gson.toJson(books, bookWriter);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public List<Book> readJsonBooks(){
        List<Book> books;

        try(FileReader booksReader = new FileReader("src/main/resources/data/books.json")){
            Type bookListType = new TypeToken<List<Book>>(){}.getType();
            books = gson.fromJson(booksReader, bookListType);

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return books;
    }

    public List<Order> readJsonOrders(){
        List<Order> orders;

        try(FileReader ordersReader = new FileReader("src/main/resources/data/orders.json")){
            Type orderListType = new TypeToken<List<Order>>(){}.getType();
            orders = gson.fromJson(ordersReader, orderListType);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return orders;
    }
}
