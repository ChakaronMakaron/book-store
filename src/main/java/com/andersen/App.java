package com.andersen;

public class App {

    private static final App instance = new App();

    public static App getInstance() {
        return instance;
    }

    private boolean running;

    private App() {
        this.running = true;
    }

    public void stop() {
        this.running = false;
    }

    public boolean isRunning() {
        return running;
    }

    public void help() {
        System.out.println("""

                order create - create new order
                order list - show the list of orders in natural order
                order list (price/date/status) - show sorted list with chosen parameter
                order complete (id) - change order status to "COMPLETED"
                order cancel (id) - change order status to "CANCELED"
                exit - stop the program
                help - print all available commands
                """);
    }
}
