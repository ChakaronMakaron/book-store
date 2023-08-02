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
}
