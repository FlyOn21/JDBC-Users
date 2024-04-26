package org.example.app.controllers;

import org.example.app.db_connect.DbConnectInit;
import org.example.app.exceptions.ConnectException;
import org.example.app.views.AppView;

import java.util.List;
import java.util.Scanner;

public class AppController {
    Scanner scanner;
    public void runApp() {
        scanner = new Scanner(System.in);
        DbConnectInit connectInit = new DbConnectInit();
        if(connectInit.isConnected()) {
            AppView appView = new AppView();
            appView.appViewProcessing(scanner, connectInit.getConnection());
            scanner.close();
            connectInit.closeConnection();
        }
        else {
            List<String> errors = connectInit.getConnectErrors();
            StringBuilder errorString = new StringBuilder();
            for (String error : errors) {
                errorString.append(error).append("\n");
            }
            scanner.close();
            throw new ConnectException(errorString.toString());
        }
    }
}
