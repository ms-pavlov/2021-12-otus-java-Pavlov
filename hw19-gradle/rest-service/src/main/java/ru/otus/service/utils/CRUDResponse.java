package ru.otus.service.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

public class CRUDResponse {
    public static ResponseEntity<?> create(Object body, BindingResult bindingResult) {
        return (null == body) || (bindingResult.hasErrors()) ?
                ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult) :
                ResponseEntity.status(HttpStatus.OK).body(body);
    }

    public static void addBindingResult(String name, String message, BindingResult bindingResult) {
        bindingResult.addError(new ObjectError(name, message));
    }

}
