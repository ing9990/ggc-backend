package com.gigacoffeebackend.global.aop;

import com.gigacoffeebackend.global.aop.annotation.AlphabetOnly;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HandleAlphabetOnly implements ConstraintValidator<AlphabetOnly, String> {

    @Override
    public boolean isValid(String value,
        ConstraintValidatorContext context) {
        return isAlphabet(value);
    }

    private boolean isAlphabet(String value) {
        return value.matches("^[a-zA-Z]+$");
    }
}