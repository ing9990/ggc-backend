package com.gigacoffeebackend.product.dto;

import com.gigacoffeebackend.global.exceptions.BusinessException;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.util.Objects;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.PRODUCT_PRICE_IS_INVALID;

@Embeddable
public class ProductPrice {

    // 100원 단위로 허용
    // -> 이 책임을 누가 가질 것인지 다시 생각해봐야댐
    private static final int DIVIDER = 100;

    @Column(name = "product_price", nullable = false)
    private int price;

    protected ProductPrice() {
    }

    public ProductPrice(int price) {
        this.price = price;
    }

    public void throwIsNotPositive() {
        if (!isPositive()) {
            throw new BusinessException(PRODUCT_PRICE_IS_INVALID);
        }
    }

    public void throwIsNotDivisibleBy100() {
        if (!isDivisible()) {
            throw new BusinessException(PRODUCT_PRICE_IS_INVALID);
        }
    }

    private boolean isPositive() {
        return this.price > 0;
    }

    private boolean isDivisible() {
        return this.price % DIVIDER == 0;
    }

    public int getValue() {
        return this.price;
    }

    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProductPrice productPrice = (ProductPrice) o;
        return productPrice.price == price;
    }

    public int hashCode() {
        return Objects.hash(price);
    }
}
