package com.gigacoffeebackend.history.domain;

import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Target(TYPE)
@Retention(RUNTIME)
@ActiveProfiles("test")
public @interface TestProfile {

}
