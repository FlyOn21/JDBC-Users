package org.example.app.utils.validate.validate_entity;

import java.util.ArrayList;
import java.util.List;

public class ValidateAnswer {
    private boolean isValid;
    private final List<String> errorsList = new ArrayList<>();

    public ValidateAnswer() {
            this.isValid = false;
        }
    public ValidateAnswer(String error) {
        this.isValid = false;
        this.errorsList.add(error);
    }

    public boolean isValid() {
        return this.isValid;
    }

    public List<String> getErrorsList() {
        return errorsList;
    }

    public void addError(String error) {
        this.errorsList.add(error);
    }

    public void setIsValid(boolean isValid) {
        this.isValid = isValid;
    }
}
