package ru.otus.requests.utils;

import org.springframework.validation.BindException;

public class ValidationCheckUtils {
    public static String getLongString(int length) {
        String longName = "A";
        for (var i = 0; i < length; i++) {
            longName = longName.concat("a");
        }
        return longName;
    }

    public static boolean hasErrors(Object obj, FieldValidatorChecker validatorChecker) {
        var bindingResult = new BindException(obj, "department");
        validatorChecker.getValidator().validate(obj, bindingResult);
        bindingResult.getFieldErrors(validatorChecker.getField()).forEach(fieldError -> validatorChecker.getLogger().info("field {} has error {}", fieldError.getField(), fieldError.getDefaultMessage()));
        return bindingResult.hasFieldErrors(validatorChecker.getField());
    }
}
