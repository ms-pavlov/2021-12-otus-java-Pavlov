package ru.otus.requests.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.Validator;

public class FieldValidatorCheckerImpl implements FieldValidatorChecker{
    private static final Logger log = LoggerFactory.getLogger(FieldValidatorChecker.class);

    private final Validator validator;
    private final String field;

    public FieldValidatorCheckerImpl(String field, Validator validator) {
        this.validator = validator;
        this.field = field;
    }

    @Override
    public Validator getValidator() {
        return validator;
    }

    @Override
    public Logger getLogger() {
        return log;
    }

    @Override
    public String getField() {
        return field;
    }
}
