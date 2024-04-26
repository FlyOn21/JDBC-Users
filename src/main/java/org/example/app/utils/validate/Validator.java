package org.example.app.utils.validate;

import org.example.app.utils.validate.enums.IValidateUnit;
import org.example.app.utils.validate.validate_entity.ValidateAnswer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator<T extends IValidateUnit> {
    public ValidateAnswer validate(String value, T field) {
        ValidateAnswer answer = new ValidateAnswer();
        Pattern pattern = Pattern.compile(field.getValidationRegex());
        Matcher matcher = pattern.matcher(value);
        if (!matcher.matches()) {
            answer.addError(field.getErrorMsg());
            return answer;
        }
        answer.setIsValid(answer.getErrorsList().isEmpty());
        return answer;
    }
}
