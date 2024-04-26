package org.example.app.utils;

import java.util.ArrayList;
import java.util.List;

public class ActionAnswer {

    private boolean isSuccess;
    private final List<String> errors = new ArrayList<>();
    private String successMsg;


    public ActionAnswer() {
        this.isSuccess = false;
    }
    public ActionAnswer(boolean isSuccess, String err) {
        this.isSuccess = isSuccess;
        this.errors.add(err);
    }

    public boolean isSuccess() {
        return this.isSuccess;
    }

    public List<String> getErrors() {
        return this.errors;
    }

    public void addError(String error) {
        this.errors.add(error);
    }

    public void setIsSuccess() {
        this.isSuccess = true;
    }

    public void setSuccessMsg(String successMsg) {
        this.successMsg = successMsg;
    }

    public String getSuccessMsg() {
        return this.successMsg;
    }
}
