package org.example.app.views;

import org.example.app.model.CreatUserModel;
import org.example.app.utils.ActionAnswer;
import org.example.app.utils.ValidateUtils;
import org.example.app.utils.validate.validate_entity.ValidateAnswer;

import java.sql.Connection;
import java.util.Scanner;

public class CreateUserView {
    private final Scanner scanner;
    private final Connection connection;
    private boolean isCorrect = false;
    String createUserMenu = """
            
            Create user menu
            
            1. Create user
            2. Back
            """;

    public CreateUserView(Scanner scanner, Connection connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public void createUserViewProcessing() {
        while (true) {
            System.out.println(createUserMenu);
            System.out.print("Select action: ");
            String action = scanner.nextLine();
            switch (action) {
                case "1":
                    CreatUserModel creatUserModel = new CreatUserModel();
                    while (!isCorrect) {
                        System.out.print("Enter first name: ");
                        String firstName = scanner.nextLine();
                        ValidateAnswer validateFirstName = creatUserModel.setFirstName(firstName);
                        isCorrect = ValidateUtils.validateProcessing(validateFirstName);
                    }
                    isCorrect = false;
                    while (!isCorrect) {
                        System.out.print("Enter last name: ");
                        String lastName = scanner.nextLine();
                        ValidateAnswer validateLastName = creatUserModel.setLastName(lastName);
                        isCorrect = ValidateUtils.validateProcessing(validateLastName);
                    }
                    isCorrect = false;
                    while (!isCorrect) {
                        System.out.print("Enter email: ");
                        String email = scanner.nextLine();
                        ValidateAnswer validateEmail = creatUserModel.setEmail(email);
                        isCorrect = ValidateUtils.validateProcessing(validateEmail);
                    }
                    isCorrect = false;
                    while (!isCorrect) {
                        System.out.print("Enter phone: ");
                        String phone = scanner.nextLine();
                        ValidateAnswer validatePhone = creatUserModel.setPhone(phone);
                        isCorrect = ValidateUtils.validateProcessing(validatePhone);
                    }
                    isCorrect = false;
                    ActionAnswer create = creatUserModel.createUser(connection);
                    if (create.isSuccess()) {
                        System.out.println(create.getSuccessMsg());
                    } else {
                        create.getErrors().forEach(System.out::println);
                    }
                    break;
                case "2":
                    System.out.println("Back..");
                    return;
                default:
                    System.out.println("Invalid action");
                    break;
            }
        }
    }
}

