package com.andersen;

import static com.andersen.authorization.Authenticator.authenticate;
import static com.andersen.authorization.Authenticator.isAuthenticated;
import static java.lang.System.in;
import static java.lang.System.out;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.andersen.controllers.impl.OrderControllerImpl;
import com.andersen.controllers.mapper.InputToControllerMapper;
import com.andersen.models.ParsedInput;
import com.andersen.repositories.BookRepository;
import com.andersen.repositories.OrderRepository;
import com.andersen.repositories.RequestRepository;
import com.andersen.services.impl.BookServiceImpl;
import com.andersen.services.impl.OrderServiceImpl;
import com.andersen.services.impl.RequestServiceImpl;
import com.andersen.utils.CommandUtils;

public class App {

    private static boolean running = true;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
        InputToControllerMapper inputToControllerMapper = new InputToControllerMapper(
                null,
                new OrderControllerImpl(
                        new BookServiceImpl(new BookRepository()),
                        new OrderServiceImpl(new OrderRepository(), new RequestServiceImpl(new RequestRepository()), new BookServiceImpl(new BookRepository()))
                ),
                null);

        out.println("Book store");
        out.println("Type 'exit' to close");
        out.println();

        while (running) {
            if (!isAuthenticated()) authenticate(scanner);
            out.print(">>> ");
            try {

                ParsedInput parsedCommand = CommandUtils.parseInput(scanner.nextLine());
                inputToControllerMapper.sendToController(parsedCommand);

            } catch (Exception e) {
                out.println(e.getMessage());
                out.println("Type 'exit' to close");
                out.println();
            }
        }

        scanner.close();
    }
}
