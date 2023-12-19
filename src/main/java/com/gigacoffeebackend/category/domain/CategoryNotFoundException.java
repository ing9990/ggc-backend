package com.gigacoffeebackend.category.domain;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.global.exceptions.ErrorCode;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.CATEGORY_NOT_FOUND;

public class CategoryNotFoundException extends BusinessException {

    public CategoryNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public CategoryNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public CategoryNotFoundException() {
        super(CATEGORY_NOT_FOUND);
    }
}
