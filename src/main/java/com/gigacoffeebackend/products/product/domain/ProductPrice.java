package com.gigacoffeebackend.products.product.domain;

import com.gigacoffeebackend.global.exceptions.BusinessException;

import com.gigacoffeebackend.products.option.domain.OptionPrice;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.PRODUCT_PRICE_IS_INVALID;

@Embeddable
public class ProductPrice {

    @Column(name = "product_price", nullable = false)
    private final BigDecimal price;

    public ProductPrice(final int price) {
        this.price = BigDecimal.valueOf(price);
        isValid();
    }

    public ProductPrice(final BigDecimal price) {
        this.price = price;
        isValid();
    }

    protected ProductPrice() {
        price = BigDecimal.ZERO;
    }

    public void throwIsNotPositive() {
        if (!isPositive()) {
            throw new BusinessException(PRODUCT_PRICE_IS_INVALID);
        }
    }

    public void throwIsNotDivisibleBy100() {
        if (!isDivisibleBy100()) {
            throw new BusinessException(PRODUCT_PRICE_IS_INVALID);
        }
    }

    public BigDecimal getValue() {
        return this.price;
    }

    private void isValid() {
        throwIsNotDivisibleBy100();
        throwIsNotPositive();
    }

    private boolean isPositive() {
        return price.compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean isDivisibleBy100() {
        return price.remainder(BigDecimal.valueOf(100)).equals(BigDecimal.ZERO);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductPrice productPrice = (ProductPrice) o;
        return productPrice.price.compareTo(price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}