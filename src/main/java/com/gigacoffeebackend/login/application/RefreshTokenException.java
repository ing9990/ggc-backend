package com.gigacoffeebackend.login.application;

import com.gigacoffeebackend.login.ui.AuthException;
import com.gigacoffeebackend.global.exceptions.ErrorCode;
import lombok.Getter;

@Getter
public class RefreshTokenException extends AuthException {

    public RefreshTokenException(final ErrorCode exceptionCode) {
        super(exceptionCode);
    }
}