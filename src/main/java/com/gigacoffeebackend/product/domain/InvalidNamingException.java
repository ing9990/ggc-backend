package com.gigacoffeebackend.product.domain;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.global.exceptions.ErrorCode;

public class InvalidNamingException extends BusinessException {

    public InvalidNamingException(String message, String value, ErrorCode errorCode) {
        super(message + ": [" + value + "]", errorCode);
    }

    public InvalidNamingException(ErrorCode errorCode) {
        super(errorCode);
    }
}
