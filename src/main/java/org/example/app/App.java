package org.example.app;

import org.example.app.controllers.AppController;

public class App {
    private final AppController startApp = new AppController();

    public void run() {
        startApp.runApp();
    }

   public static void main(String[] args) {
        new App().run();
    }
}
