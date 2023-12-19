package com.gigacoffeebackend.products.option.domain;

import javax.persistence.Embeddable;

@Embeddable
public class OptionName {

    private String value;

    public OptionName() {
    }

    public OptionName(final String value) {
        validateOptionName(value);
        this.value = value;
    }

    private void validateOptionName(String value) {

    }

    public String toString() {
        return value;
    }
}
