package org.example.app.views;

import org.example.app.db_connect.DbConnectInit;
import org.example.app.entity.User;
import org.example.app.model.GetUsersModel;
import org.example.app.utils.ValidateUtils;
import org.example.app.utils.validate.validate_entity.ValidateAnswer;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GetUsersView {
    private final Scanner scanner;
    private final DbConnectInit connection;
    private boolean isCorrect = false;
    String createUserMenu = """
            
            View all users menu

            1. View all users without limit, offset, exclude_columns
            2. View all users with limit, offset, exclude_columns
            3. Back
            """;

    public GetUsersView(Scanner scanner, DbConnectInit connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public void getUsersViewProcessing() {
        GetUsersModel getUsersModel = new GetUsersModel();
        while (true) {
            System.out.println(createUserMenu);
            System.out.print("Select action: ");
            String action = scanner.nextLine();
            switch (action) {
                case "1":
                    Optional<List<User>> resultWithoutFilters= getUsersModel.getUsers(connection);
                    if (resultWithoutFilters.isPresent()) {
                        resultWithoutFilters.get().forEach(System.out::println);
                    } else {
                        System.out.println("No users found");
                    }
                    break;
                case "2":
                    while (!isCorrect) {
                        System.out.print("Enter limit: ");
                        String limit = scanner.nextLine();
                        ValidateAnswer limitAnswer = getUsersModel.setLimit(limit);
                        isCorrect = ValidateUtils.validateProcessing(limitAnswer);
                    }
                    isCorrect = false;
                    while (!isCorrect) {
                        System.out.print("Enter offset: ");
                        String offset = scanner.nextLine();
                        ValidateAnswer offsetAnswer = getUsersModel.setOffset(offset);
                        isCorrect = ValidateUtils.validateProcessing(offsetAnswer);
                    }
                    isCorrect = false;
                    while (!isCorrect) {
                        System.out.print("Enter exclude_columns (string comma separated or empty string): ");
                        String excludeColumns = scanner.nextLine();
                        if (!excludeColumns.isEmpty()) {
                            ValidateAnswer excludeColumnsAnswer = getUsersModel.setExcludeColumns(excludeColumns);
                            isCorrect = ValidateUtils.validateProcessing(excludeColumnsAnswer);
                        } else {
                            isCorrect = true;
                        }
                    }
                    isCorrect = false;
                    Optional<List<User>> resultWithFilters =  getUsersModel.getUsers(connection);
                    if (resultWithFilters.isPresent()) {
                        resultWithFilters.get().forEach(System.out::println);
                    } else {
                        System.out.println("No users found");
                    }
                    break;
                case "3":
                    System.out.println("Back..");
                    return;
                default:
                    System.out.println("Invalid action");
                    break;
            }
        }
    }
}
