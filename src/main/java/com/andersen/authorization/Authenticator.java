package com.andersen.authorization;

import static java.lang.System.out;

import java.util.Scanner;

import com.andersen.authorization.models.User;

public class Authenticator {

    private static boolean isAuthenticated = false;
    private static User user = new User(1L, "root", "pass");

    private Authenticator() {}

    public static void authenticate(Scanner scanner) {
        out.print("Your login: ");
        String login = scanner.nextLine();
        if (!user.getLogin().equals(login)) throw new IllegalArgumentException("User not found");
        out.print("Password: ");
        String password = scanner.nextLine();
        if (!user.getPassword().equals(password)) throw new IllegalArgumentException("Wrong password");
        isAuthenticated = true;
    }

    public static User getUser() {
        return user;
    }

    public static boolean isAuthenticated() {
        return isAuthenticated;
    }
}
