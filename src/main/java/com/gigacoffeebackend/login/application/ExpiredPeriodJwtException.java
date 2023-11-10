package com.gigacoffeebackend.login.application;

import com.gigacoffeebackend.auth.AuthException;
import com.gigacoffeebackend.global.exceptions.ErrorCode;
import lombok.Getter;

@Getter
public class ExpiredPeriodJwtException extends AuthException {

    public ExpiredPeriodJwtException(final ErrorCode exceptionCode) {
        super(exceptionCode);
    }
}
