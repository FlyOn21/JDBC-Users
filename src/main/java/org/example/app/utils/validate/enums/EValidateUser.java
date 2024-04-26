package org.example.app.utils.validate.enums;

public enum EValidateUser implements IValidateUnit {
    FIRST_NAME("First name must contain only letters and min length 3 characters", "^[A-Za-z]{3,}$"),
    LAST_NAME ("Last name must contain only letters and min length 3 characters", "^[A-Za-z]{3,}$"),
    EMAIL("Email must be a valid email address", "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$"),
    PHONE("Phone number must be a valid phone number. E164 iso format. Example +380505555555", "\\(?\\+[0-9]{1,3}\\)? ?-?[0-9]{1,3} ?-?[0-9]{3,5} ?-?[0-9]{4}( ?-?[0-9]{3})?");

    private final String errorMsg;
    private final String validationRegex;


    EValidateUser(String errorMsg, String validationRegex) {
        this.errorMsg = errorMsg;
        this.validationRegex = validationRegex;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public String getValidationRegex() {
        return this.validationRegex;
    }
}
