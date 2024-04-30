package org.example.app.model;

import org.example.app.db_connect.DbConnectInit;
import org.example.app.entity.User;
import org.example.app.repository.UserRepository;
import org.example.app.utils.ActionAnswer;
import org.example.app.utils.validate.Validator;
import org.example.app.utils.validate.enums.EValidateQuery;
import org.example.app.utils.validate.validate_entity.ValidateAnswer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DeleteUserModel {
    private Long id;

    public DeleteUserModel() {
    }

    public Long getId() {
        return id;
    }

    public ValidateAnswer setId(String id) {
        Validator<EValidateQuery> validator = new Validator<>();
        ValidateAnswer answer = validator.validate(id, EValidateQuery.ID);
        if (answer.isValid()) {
            this.id = Long.parseLong(id);
            return answer;
        }
        return answer;
    }

    public ActionAnswer deleteUser(DbConnectInit connection) {
        UserRepository userRepository = new UserRepository(connection);
        Optional<List<User>> answerGetById = userRepository.readById(this.id, new ArrayList<>());
        if (answerGetById.isPresent()) {
            List<User> users = answerGetById.get();
            if (users.size() == 1) {
                return userRepository.delete(this.id);
            }
        }
        return new ActionAnswer(false, "User not found");
    }
}
