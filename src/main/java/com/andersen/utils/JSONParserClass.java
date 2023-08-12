package com.andersen.utils;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.List;

import com.andersen.models.Book;
import com.andersen.models.Order;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class JSONParserClass {

    Gson gson = new GsonBuilder()
            .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
            .setPrettyPrinting()
            .create();

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
