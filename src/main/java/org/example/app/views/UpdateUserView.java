package org.example.app.views;

import org.example.app.db_connect.DbConnectInit;
import org.example.app.entity.User;
import org.example.app.model.GetByIdUserModel;
import org.example.app.model.UpdateUserModel;
import org.example.app.utils.ActionAnswer;
import org.example.app.utils.ValidateUtils;
import org.example.app.utils.validate.validate_entity.ValidateAnswer;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UpdateUserView {
    private final Scanner scanner;
    private final DbConnectInit connection;
    private boolean isCorrect = false;
    String updateUserMenu = """
            
            Update user menu
            
            1. Update user by id
            2. Back
            """;

    public UpdateUserView(Scanner scanner, DbConnectInit connection) {
        this.scanner = scanner;
        this.connection = connection;
    }

    public void createUserViewProcessing() {
        UpdateUserModel updateUserModel = new UpdateUserModel();
        GetByIdUserModel getByIdUserModel = new GetByIdUserModel();
        while (true) {
            System.out.println(updateUserMenu);
            System.out.print("Select action: ");
            String action = scanner.nextLine();
            switch (action) {
                case "1":
                    while (!isCorrect) {
                        System.out.print("Enter id user for update: ");
                        String id = scanner.nextLine();
                        ValidateAnswer validateId = getByIdUserModel.setId(id);
                        isCorrect = ValidateUtils.validateProcessing(validateId);
                    }
                    isCorrect = false;
                    Optional<List<User>> currentUser = getByIdUserModel.getUser(connection);
                    if (currentUser.isPresent()) {
                        User user = currentUser.get().getFirst();
                        updateUserModel.setUser(user);
                        System.out.println("Current user: " + user);
                        while (!isCorrect) {
                            System.out.print("Enter first name (if update unnecessary press Enter): ");
                            String firstName = scanner.nextLine();
                            if (firstName.isEmpty()) {
                                firstName = user.getFirstName();
                            }
                            ValidateAnswer validateFirstName = updateUserModel.setFirstName(firstName);
                            isCorrect = ValidateUtils.validateProcessing(validateFirstName);
                        }
                        isCorrect = false;
                        while (!isCorrect) {
                            System.out.print("Enter last name (if update unnecessary press Enter): ");
                            String lastName = scanner.nextLine();
                            if (lastName.isEmpty()) {
                                lastName = user.getLastName();
                            }
                            ValidateAnswer validateLastName = updateUserModel.setLastName(lastName);
                            isCorrect = ValidateUtils.validateProcessing(validateLastName);
                        }
                        isCorrect = false;
                        while (!isCorrect) {
                            boolean selfEmail = false;
                            System.out.print("Enter email (if update unnecessary press Enter): ");
                            String email = scanner.nextLine();
                            if (email.isEmpty()) {
                                email = user.getEmail();
                                selfEmail = true;
                            }
                            ValidateAnswer validateEmail = updateUserModel.setEmail(email, connection, selfEmail);
                            isCorrect = ValidateUtils.validateProcessing(validateEmail);
                        }
                        isCorrect = false;
                        while (!isCorrect) {
                            System.out.print("Enter phone (if update unnecessary press Enter): ");
                            String phone = scanner.nextLine();
                            if (phone.isEmpty()) {
                                phone = user.getPhone();
                            }
                            ValidateAnswer validatePhone = updateUserModel.setPhone(phone);
                            isCorrect = ValidateUtils.validateProcessing(validatePhone);
                        }
                        isCorrect = false;
                        ActionAnswer update = updateUserModel.updateUser(connection);
                        if (update.isSuccess()) {
                            System.out.println(update.getSuccessMsg());
                        } else {
                            update.getErrors().forEach(System.out::println);
                        }
                    } else {
                        System.out.println("No users found");
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
