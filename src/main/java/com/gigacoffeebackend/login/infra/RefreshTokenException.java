package com.gigacoffeebackend.login.infra;

import com.gigacoffeebackend.auth.application.AuthException;
import com.gigacoffeebackend.global.exceptions.ErrorCode;
import lombok.Getter;

@Getter
public class RefreshTokenException extends AuthException {

    public RefreshTokenException(final ErrorCode exceptionCode) {
        super(exceptionCode);
    }
}
