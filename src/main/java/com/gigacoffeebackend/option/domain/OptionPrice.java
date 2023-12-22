package com.gigacoffeebackend.option.domain;

import static java.math.BigDecimal.ZERO;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Getter;

@Embeddable
@Getter
public class OptionPrice {

    @Column(name = "option_price", nullable = false)
    private final BigDecimal value;

    public OptionPrice(final BigDecimal value) {
        this.value = value;
    }

    public OptionPrice(final int value) {
        this.value = BigDecimal.valueOf(value);
    }

    public static OptionPrice free() {
        return new OptionPrice(ZERO);
    }

    protected OptionPrice() {
        value = ZERO;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
