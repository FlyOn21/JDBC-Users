package org.example.app.model;

import org.example.app.entity.User;
import org.example.app.repository.UserRepository;
import org.example.app.utils.ActionAnswer;
import org.example.app.utils.validate.Validator;
import org.example.app.utils.validate.enums.EValidateUser;
import org.example.app.utils.validate.validate_entity.ValidateAnswer;

import java.sql.Connection;

public class UpdateUserModel {
    private User user;

    public UpdateUserModel() {
    }

    public void setUser (User user) {
        this.user = user;
    }

    public ValidateAnswer setFirstName(String firstName) {
        Validator<EValidateUser> validator = new Validator<>();
        ValidateAnswer answer = validator.validate(firstName, EValidateUser.FIRST_NAME);
        if (answer.isValid()) {
            this.user.setFirstName(firstName);
            return answer;
        }
        return answer;
    }

    public ValidateAnswer setLastName(String lastName) {
        Validator<EValidateUser> validator = new Validator<>();
        ValidateAnswer answer = validator.validate(lastName, EValidateUser.LAST_NAME);
        if (answer.isValid()) {
            this.user.setLastName(lastName);
            return answer;
        }
        return answer;
    }

    public ValidateAnswer setEmail(String email, Connection connection, boolean isSelfEmail) {
        Validator<EValidateUser> validator = new Validator<>();
        ValidateAnswer answer = validator.validate(email, EValidateUser.EMAIL);
        if (answer.isValid()) {
            if (!isSelfEmail) {
                if (isEmailExists(connection)) {
                    ValidateAnswer answerCheckEmail = new ValidateAnswer();
                    answerCheckEmail.addError("Email already exists");
                    return answerCheckEmail;
                }
            }
            this.user.setEmail(email);
            return answer;
        }
        return answer;
    }

    public ValidateAnswer setPhone(String phone) {
        Validator<EValidateUser> validator = new Validator<>();
        ValidateAnswer answer = validator.validate(phone, EValidateUser.PHONE);
        if (answer.isValid()) {
            this.user.setPhone(phone);
            return answer;
        }
        return answer;
    }

    public ActionAnswer updateUser(Connection connection) {
        UserRepository userRepository = new UserRepository(connection);
        return userRepository.update(this.user);
    }

    private boolean isEmailExists(Connection connection) {
        UserRepository userRepository = new UserRepository(connection);
        return userRepository.checkEmailExists(this.user.getEmail());
    }

}
