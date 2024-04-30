package org.example.app.model;

import org.example.app.db_connect.DbConnectInit;
import org.example.app.entity.User;
import org.example.app.repository.UserRepository;
import org.example.app.utils.AnnotationUtils;
import org.example.app.utils.validate.Validator;
import org.example.app.utils.validate.enums.EValidateQuery;
import org.example.app.utils.validate.validate_entity.ValidateAnswer;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GetByIdUserModel {

    private Long id;
    private List<String> excludeColumns;


    public GetByIdUserModel() {
        this.excludeColumns = new ArrayList<>();
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
    public void setIdLong(Long id) {
        this.id = id;
    }

    public ValidateAnswer setExcludeColumns(String excludeColumns) {
        Validator<EValidateQuery> validator = new Validator<>();
        ValidateAnswer answer = validator.validate(excludeColumns, EValidateQuery.EXCLUDE_COLUMNS);
        List<String> columnsNotInTable = new ArrayList<>();
        if (answer.isValid()) {
            List<String> columnsFromUser= List.of(excludeColumns.split(","));
            AnnotationUtils annotationUtils = new AnnotationUtils();
            List<String> columnsInTable = annotationUtils.getColumnNames(User.class);
            columnsFromUser.forEach(column -> {
                if (!columnsInTable.contains(column)) {
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

    public Optional<List<User>> getUser(DbConnectInit connection) {
        UserRepository userRepository = new UserRepository(connection);
        return userRepository.readById(this.id, this.excludeColumns);
    }
}
