package com.gigacoffeebackend.auth.application;

import com.gigacoffeebackend.global.exceptions.ErrorCode;
import lombok.Getter;

@Getter
public class AuthException extends RuntimeException {

    private final ErrorCode errorCode;

    public AuthException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
