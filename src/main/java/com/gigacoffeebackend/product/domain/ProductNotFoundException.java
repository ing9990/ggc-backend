package com.gigacoffeebackend.product.domain;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.global.exceptions.ErrorCode;
import lombok.Getter;

@Getter
public class ProductNotFoundException extends BusinessException {

    public ProductNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

    public ProductNotFoundException() {
        super(ErrorCode.PRODUCT_NOT_FOUND);
    }

}
