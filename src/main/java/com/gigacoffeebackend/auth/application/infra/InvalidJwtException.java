package com.gigacoffeebackend.auth.application.infra;

import com.gigacoffeebackend.auth.application.AuthException;
import com.gigacoffeebackend.global.exceptions.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidJwtException extends AuthException {

    public InvalidJwtException(final ErrorCode errorCode) {
        super(errorCode);
    }
}
