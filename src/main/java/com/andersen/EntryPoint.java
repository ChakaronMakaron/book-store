package com.andersen;

import com.andersen.authorization.Authenticator;
import com.andersen.controllers.impl.BookControllerCommandLine;
import com.andersen.controllers.impl.OrderControllerCommandLine;
import com.andersen.controllers.impl.RequestControllerCommandLine;
import com.andersen.controllers.router.InputToControllerRouter;
import com.andersen.models.ParsedInput;
import com.andersen.repositories.BookRepository;
import com.andersen.repositories.OrderRepository;
import com.andersen.repositories.RequestRepository;
import com.andersen.services.impl.BookServiceImpl;
import com.andersen.services.impl.OrderServiceImpl;
import com.andersen.services.impl.RequestServiceImpl;
import com.andersen.utils.InputParser;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EntryPoint {


    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        InputToControllerRouter inputToControllerMapper = new InputToControllerRouter(
                new BookControllerCommandLine(new BookServiceImpl(new BookRepository())),
                new OrderControllerCommandLine(
                    new BookServiceImpl(new BookRepository()),
                    new OrderServiceImpl(new OrderRepository(), new RequestServiceImpl(new RequestRepository()), new BookServiceImpl(new BookRepository()))),
                new RequestControllerCommandLine(new RequestServiceImpl(new RequestRepository())));

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
