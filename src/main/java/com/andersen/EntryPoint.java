package com.andersen;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

import com.andersen.authorization.Authenticator;
import com.andersen.controllers.impl.OrderControllerCommandLine;
import com.andersen.controllers.router.InputToControllerRouter;
import com.andersen.models.Book;
import com.andersen.models.ParsedInput;
import com.andersen.repositories.BookRepository;
import com.andersen.repositories.OrderRepository;
import com.andersen.repositories.RequestRepository;
import com.andersen.services.impl.BookServiceImpl;
import com.andersen.services.impl.OrderServiceImpl;
import com.andersen.services.impl.RequestServiceImpl;
import com.andersen.utils.InputParser;

public class EntryPoint {


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        List<Book> books = List.of(
                new Book(1L,"The Great Gatsby", 39, 5),
                new Book(2L, "Lolita", 25, 3),
                new Book(3L, "The Catcher in the Rye", 22, 2),
                new Book(4L, "Don Quixote", 42, 9),
                new Book(5L, "The Grapes of Wrath", 33, 3),
                new Book(6L, "Beloved", 17, 4),
                new Book(7L, "Catch-22", 20, 6),
                new Book(8L, "To Kill a Mockingbird", 25, 2),
                new Book(9L, "Frankenstein", 15, 1),
                new Book(10L, "Ulysses", 31, 1),
                new Book(11L, "Alice in Wonderland", 25, 3),
                new Book(12L, "Anna Karenina", 27, 1)
        );

        InputToControllerRouter inputToControllerMapper = new InputToControllerRouter(null,
                new OrderControllerCommandLine(
                    new BookServiceImpl(new BookRepository(books)),
                    new OrderServiceImpl(new OrderRepository(), new RequestServiceImpl(new RequestRepository()), new BookServiceImpl(new BookRepository(books)))),
                null);

        System.out.println("Book store");
        System.out.println("Type 'exit' to close");
        System.out.println();

        while (App.getInstance().isRunning()) {
            if (!Authenticator.getInstance().isAuthenticated()) Authenticator.getInstance().authenticate(scanner);
            System.out.print(">>> ");
            try {

                ParsedInput parsedInput = InputParser.getInstance().parseInput(scanner.nextLine());
                inputToControllerMapper.sendToController(parsedInput);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Type 'exit' to close");
                System.out.println();
            }
        }
    }
}