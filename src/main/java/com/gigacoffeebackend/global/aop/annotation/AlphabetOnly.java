package com.gigacoffeebackend.global.aop.annotation;

import com.gigacoffeebackend.global.aop.HandleAlphabetOnly;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME)
@Constraint(validatedBy = HandleAlphabetOnly.class)
@Target(FIELD)
public @interface AlphabetOnly {
    String message() default "영문만 입력해주세요.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
