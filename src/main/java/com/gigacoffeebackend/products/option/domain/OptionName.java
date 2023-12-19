package com.gigacoffeebackend.products.option.domain;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.global.exceptions.ErrorCode;
import java.util.regex.Pattern;
import javax.persistence.Embeddable;

@Embeddable
public class OptionName {

    private static final Pattern regex = Pattern.compile("[a-zA-Z0-9가-힣\\s\\[\\]\\(\\),\\.]+");

    private String value;

    public OptionName() {

    }

    public OptionName(final String value) {
        validateOptionName(value);
        this.value = value;
    }

    private void validateOptionName(String value) {
        if (!regex.matcher(value).matches()) {
            throw new BusinessException(ErrorCode.INVALID_OPTION_NAME);
        }
    }

    public String toString() {
        return value;
    }
}
