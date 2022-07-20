package ru.otus.requests.utils;

import org.slf4j.Logger;
import org.springframework.validation.Validator;

public interface FieldValidatorChecker {

    Validator getValidator();

    Logger getLogger();

    String getField();
}
