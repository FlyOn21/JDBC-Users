package org.example.app.views;

import org.example.app.controllers.CRUDController;

import java.sql.Connection;
import java.util.Scanner;

public class AppView {
    String title = "Project JDBC User";

    String menu = """
            1. Get all users
            2. Create user
            3. Find user by id
            4. Update user
            5. Delete user
            6. Exit
            """;

    public void appViewProcessing(Scanner scanner, Connection connection) {
        System.out.println(title);
        while (true) {
            System.out.println(menu);
            System.out.print("Select action: ");
            String action = scanner.nextLine();
            CRUDController crudController = new CRUDController();
            switch (action) {
                case "1":
                    crudController.readAll(scanner, connection);
                    break;
                case "2":
                    crudController.create(scanner, connection);
                    break;
                case "3":
                    crudController.readById(scanner, connection);
                    break;
                case "4":
                    crudController.update(scanner, connection);
                    break;
                case "5":
                    crudController.delete(scanner, connection);
                    break;
                case "6":
                    System.out.println("Exit..");
                    return;
                default:
                    System.out.println("Invalid action");
                    break;
            }
        }
    }
}
