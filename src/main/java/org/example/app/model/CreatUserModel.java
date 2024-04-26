package org.example.app.model;

import org.example.app.entity.User;
import org.example.app.repository.UserRepository;
import org.example.app.utils.ActionAnswer;
import org.example.app.utils.validate.Validator;
import org.example.app.utils.validate.enums.EValidateUser;
import org.example.app.utils.validate.validate_entity.ValidateAnswer;

import java.sql.Connection;

public class CreatUserModel {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

    public CreatUserModel() {
    }

    public ValidateAnswer setFirstName(String firstName) {
        Validator<EValidateUser> validator = new Validator<>();
        ValidateAnswer answer = validator.validate(firstName, EValidateUser.FIRST_NAME);
        if (answer.isValid()) {
            this.firstName = firstName;
            return answer;
        }
        return answer;
    }

    public ValidateAnswer setLastName(String lastName) {
        Validator<EValidateUser> validator = new Validator<>();
        ValidateAnswer answer = validator.validate(lastName, EValidateUser.LAST_NAME);
        if (answer.isValid()) {
            this.lastName = lastName;
            return answer;
        }
        return answer;
    }

    public ValidateAnswer setEmail(String email) {
        Validator<EValidateUser> validator = new Validator<>();
        ValidateAnswer answer = validator.validate(email, EValidateUser.EMAIL);
        if (answer.isValid()) {
            this.email = email;
            return answer;
        }
        return answer;
    }

    public ValidateAnswer setPhone(String phone) {
        Validator<EValidateUser> validator = new Validator<>();
        ValidateAnswer answer = validator.validate(phone, EValidateUser.PHONE);
        if (answer.isValid()) {
            this.phone = phone;
            return answer;
        }
        return answer;
    }

    public ActionAnswer createUser(Connection connection) {
        User newUser = new User(firstName, lastName, email, phone);
        UserRepository userRepository = new UserRepository(connection);
        return userRepository.create(newUser);
    }
}
