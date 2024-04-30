package org.example.app.views;

import org.example.app.db_connect.DbConnectInit;
import org.example.app.entity.User;
import org.example.app.model.GetByIdUserModel;
import org.example.app.utils.ValidateUtils;
import org.example.app.utils.validate.validate_entity.ValidateAnswer;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class GetUserByIdView {
    private final Scanner scanner;
    private final DbConnectInit connection;
    private boolean isCorrect = false;
    String createUserMenu = """
            
            View current users by id menu

            1. View current users by id without exclude_columns
            2. View current users by id with exclude_columns
            3. Back
            """;

    public GetUserByIdView(Scanner scanner, DbConnectInit connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public void getUserByIdProcessing() {
        GetByIdUserModel getByIdUserModel = new GetByIdUserModel();
        while (true) {
            System.out.println(createUserMenu);
            System.out.print("Select action: ");
            String action = scanner.nextLine();
            switch (action) {
                case "1":
                    while (!isCorrect) {
                        System.out.print("Enter id: ");
                        String id = scanner.nextLine();
                        ValidateAnswer validateId = getByIdUserModel.setId(id);
                        isCorrect = ValidateUtils.validateProcessing(validateId);
                    }
                    isCorrect = false;
                    Optional<List<User>> resultByIDWithoutEC = getByIdUserModel.getUser(connection);
                    if (resultByIDWithoutEC.isPresent()) {
                        resultByIDWithoutEC.get().forEach(System.out::println);
                    } else {
                        System.out.println("No users found");
                    }
                    break;
                case "2":
                    while (!isCorrect) {
                        System.out.print("Enter id: ");
                        String id = scanner.nextLine();
                        ValidateAnswer validateId = getByIdUserModel.setId(id);
                        isCorrect = ValidateUtils.validateProcessing(validateId);
                    }
                    isCorrect = false;
                    while (!isCorrect) {
                        System.out.print("Enter exclude_columns (string comma separated or empty string): ");
                        String excludeColumns = scanner.nextLine();
                        ValidateAnswer excludeColumnsAnswer = getByIdUserModel.setExcludeColumns(excludeColumns);
                        isCorrect = ValidateUtils.validateProcessing(excludeColumnsAnswer);
                    }
                    isCorrect = false;
                    Optional<List<User>> resultByIDWithEC = getByIdUserModel.getUser(connection);
                    if (resultByIDWithEC.isPresent()) {
                        resultByIDWithEC.get().forEach(System.out::println);
                    } else {
                        System.out.println("No users found");
                    }
                    break;
                case "3":
                    System.out.println("Back..");
                    return;
                default:
                    System.out.println("Invalid action!");
            }
        }
    }
}
