package com.andersen;

import com.andersen.authorization.Authenticator;
import com.andersen.controllers.router.InputToControllerRouter;
import com.andersen.models.ParsedInput;
import com.andersen.utils.InputParser;

public class EntryPoint {
    
    public static void main(String[] args) {

        InputToControllerRouter inputToControllerMapper = new InputToControllerRouter(null, null, null);

        System.out.println("Book store");
        System.out.println("Type 'exit' to close");
        System.out.println();

        while (App.getInstance().isRunning()) {
            if (!Authenticator.getInstance().isAuthenticated()) Authenticator.getInstance().authenticate();
            System.out.print(">>> ");
            try {

                ParsedInput parsedInput = InputParser.getInstance().parseInput(System.console().readLine());
                inputToControllerMapper.sendToController(parsedInput);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                System.out.println("Type 'exit' to close");
                System.out.println();
            }
        }
    }
}
