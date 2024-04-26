package org.example.app.controllers;

import org.example.app.views.*;

import java.sql.Connection;
import java.util.Scanner;

public class CRUDController {
    public void create(Scanner scanner, Connection connection) {
        CreateUserView createUserView = new CreateUserView(scanner, connection);
        createUserView.createUserViewProcessing();
    }

    public void readAll(Scanner scanner, Connection connection) {
        GetUsersView getUsersView = new GetUsersView(scanner, connection);
        getUsersView.getUsersViewProcessing();
    }

    public void readById(Scanner scanner, Connection connection) {
        GetUserByIdView getUserByIdView = new GetUserByIdView(scanner, connection);
        getUserByIdView.getUserByIdProcessing();
    }

    public void update(Scanner scanner, Connection connection) {
        UpdateUserView updateUserView = new UpdateUserView(scanner, connection);
        updateUserView.createUserViewProcessing();
    }

    public void delete(Scanner scanner, Connection connection) {
        DeleteUserView deleteUserView = new DeleteUserView(scanner, connection);
        deleteUserView.deleteUserViewProcessing();
    }
}
