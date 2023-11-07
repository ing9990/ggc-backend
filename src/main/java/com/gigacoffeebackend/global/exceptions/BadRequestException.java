package com.gigacoffeebackend.global.exceptions;

import lombok.Getter;

@Getter
public class BadRequestException extends RuntimeException {

    private final ErrorCode errorCode;

    public BadRequestException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}
