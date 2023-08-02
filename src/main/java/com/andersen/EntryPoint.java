package com.andersen;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.andersen.authorization.Authenticator;
import com.andersen.controllers.router.InputToControllerRouter;
import com.andersen.models.ParsedInput;
import com.andersen.utils.InputParser;

public class EntryPoint {
    
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        InputToControllerRouter inputToControllerMapper = new InputToControllerRouter(null, null, null);

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

        scanner.close();
    }
}
