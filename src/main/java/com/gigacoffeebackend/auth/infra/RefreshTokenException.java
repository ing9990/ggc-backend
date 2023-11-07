package com.gigacoffeebackend.auth.infra;

import com.gigacoffeebackend.auth.ui.AuthException;
import com.gigacoffeebackend.global.exceptions.ErrorCode;
import lombok.Getter;

@Getter
public class RefreshTokenException extends AuthException {

    public RefreshTokenException(final ErrorCode exceptionCode) {
        super(exceptionCode);
    }
}
