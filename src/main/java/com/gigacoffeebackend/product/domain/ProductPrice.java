package com.gigacoffeebackend.product.domain;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.math.BigDecimal;
import java.util.Objects;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.PRODUCT_PRICE_IS_INVALID;
import static lombok.AccessLevel.PROTECTED;

@Embeddable
@NoArgsConstructor(access = PROTECTED)
public class ProductPrice {

    @Column(name = "product_price", nullable = false)
    private BigDecimal price;

    public ProductPrice(final int price) {
        this.price = BigDecimal.valueOf(price);
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

    private boolean isPositive() {
        return price.compareTo(BigDecimal.ZERO) > 0;
    }

    private boolean isDivisibleBy100() {
        return price.remainder(BigDecimal.valueOf(100)).equals(BigDecimal.ZERO);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProductPrice productPrice = (ProductPrice) o;
        return productPrice.price.compareTo(price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(price);
    }
}