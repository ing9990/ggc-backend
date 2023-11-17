package com.gigacoffeebackend.store.domain;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.global.exceptions.ErrorCode;

public class StoreNotFoundException extends BusinessException {
    public StoreNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public StoreNotFoundException() {
        super(ErrorCode.STORE_NOT_FOUND);
    }
}
