package org.example.app.model;

import org.example.app.entity.User;
import org.example.app.repository.UserRepository;
import org.example.app.tables.UsersTable;
import org.example.app.utils.validate.Validator;
import org.example.app.utils.validate.enums.EValidateQuery;
import org.example.app.utils.validate.validate_entity.ValidateAnswer;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GetUsersModel {
    private int limit;
    private int offset;
    private List<String> excludeColumns;

    public GetUsersModel() {
        this.limit = 0;
        this.offset = 0;
        this.excludeColumns = new ArrayList<>();
    }

    public ValidateAnswer setLimit(String limit) {
        Validator<EValidateQuery> validator = new Validator<>();
        ValidateAnswer answer = validator.validate(limit, EValidateQuery.LIMIT);
        if (answer.isValid()) {
            this.limit = Integer.parseInt(limit);
            return answer;
        }
        return answer;
    }

    public ValidateAnswer setOffset(String offset) {
        Validator<EValidateQuery> validator = new Validator<>();
        ValidateAnswer answer = validator.validate(offset, EValidateQuery.OFFSET);
        if (answer.isValid()) {
            this.offset = Integer.parseInt(offset);
            return answer;
        }
        return answer;
    }

    public ValidateAnswer setExcludeColumns(String excludeColumns) {
        Validator<EValidateQuery> validator = new Validator<>();
        ValidateAnswer answer = validator.validate(excludeColumns, EValidateQuery.EXCLUDE_COLUMNS);
        List<String> columnsNotInTable = new ArrayList<>();
        if (answer.isValid()) {
            List<String> columnsFromUser= List.of(excludeColumns.split(","));
            UsersTable usersTable = new UsersTable();
            columnsFromUser.forEach(column -> {
                if (!usersTable.getColumnsNames().contains(column)) {
                    columnsNotInTable.add(column);
                }
            });
            if (!columnsNotInTable.isEmpty()) {
                ValidateAnswer errorAnswer = new ValidateAnswer();
                columnsNotInTable.forEach(column -> errorAnswer.addError("Column " + column + " not in table"));
                return errorAnswer;
            }
            this.excludeColumns = columnsFromUser;
            return answer;
        }
        return answer;
    }

    public Optional<List<User>> getUsers(Connection connection) {
        UserRepository userRepository = new UserRepository(connection);
        return userRepository.readAll(this.excludeColumns, this.limit, this.offset);
    }

}
