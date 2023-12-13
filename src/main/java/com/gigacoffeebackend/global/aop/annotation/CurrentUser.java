package com.gigacoffeebackend.global.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * {@link HandleCurrentUser}
 */

@Documented
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface CurrentUser {

}
