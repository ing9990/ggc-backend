package com.gigacoffeebackend.products.domain;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.PRODUCT_PRICE_IS_INVALID;
import static org.junit.jupiter.api.Assertions.*;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.products.product.domain.ProductPrice;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductPriceTest {

    @ParameterizedTest
    @ValueSource(ints = {101, 2001, 2010, 3101, 5102})
    @DisplayName("상품 가격은 100원으로 나누어 떨어지지 않으면 예외를 던진다.")
    void throws_exception_if_product_price_rounded_by_100(int price) {
        // given
        // when // then
        Assertions.assertThatThrownBy(() -> new ProductPrice(price))
            .isInstanceOf(BusinessException.class)
            .hasMessage(PRODUCT_PRICE_IS_INVALID.getMessage());
    }


    @ParameterizedTest
    @ValueSource(ints = {1000, 2000, 3500, 4500, 5000})
    @DisplayName("상품 가격이 100원 단위로 나누어 떨어진다면 예외를 던지지 않는다.")
    void does_not_throws_exception_if_product_price_rounded_by_100(int price) {
        // given
        // when // then
        assertDoesNotThrow(() -> new ProductPrice(price));
    }

}