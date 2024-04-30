package org.example.app.controllers;

import org.example.app.db_connect.DbConnectInit;
import org.example.app.views.*;

import java.util.Scanner;

public class CRUDController {
    public void create(Scanner scanner, DbConnectInit connection) {
        CreateUserView createUserView = new CreateUserView(scanner, connection);
        createUserView.createUserViewProcessing();
    }

    public void readAll(Scanner scanner, DbConnectInit connection) {
        GetUsersView getUsersView = new GetUsersView(scanner, connection);
        getUsersView.getUsersViewProcessing();
    }

    public void readById(Scanner scanner, DbConnectInit connection) {
        GetUserByIdView getUserByIdView = new GetUserByIdView(scanner, connection);
        getUserByIdView.getUserByIdProcessing();
    }

    public void update(Scanner scanner, DbConnectInit connection) {
        UpdateUserView updateUserView = new UpdateUserView(scanner, connection);
        updateUserView.createUserViewProcessing();
    }

    public void delete(Scanner scanner, DbConnectInit connection) {
        DeleteUserView deleteUserView = new DeleteUserView(scanner, connection);
        deleteUserView.deleteUserViewProcessing();
    }
}
