package org.example.app.utils;

import org.example.app.utils.validate.validate_entity.ValidateAnswer;

public class ValidateUtils {

    public static boolean validateProcessing(ValidateAnswer answer) {
        if (answer.isValid()) {
            return true;
        }
        answer.getErrorsList().forEach(System.out::println);
        return false;
    }
}
