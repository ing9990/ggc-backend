package com.gigacoffeebackend.login.infra;

import com.gigacoffeebackend.auth.application.AuthException;
import com.gigacoffeebackend.global.exceptions.ErrorCode;
import lombok.Getter;

@Getter
public class ExpiredPeriodJwtException extends AuthException {

    public ExpiredPeriodJwtException(final ErrorCode exceptionCode) {
        super(exceptionCode);
    }
}
