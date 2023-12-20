package com.gigacoffeebackend.products.domain;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.PRODUCT_NAME_IS_EMPTY;
import static org.junit.jupiter.api.Assertions.*;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.product.domain.ProductName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class ProductNameTest {

    @DisplayName("상품 이름이 적절한지 확인할 수 있다.")
    @ParameterizedTest
    @ValueSource(strings = {"만유", "인력", "라떼", "Coffee"})
    void product_name_is_valid(String name) {
        // given

        // when //then
        assertDoesNotThrow(new ProductName(name)::isValid);
    }

    @ParameterizedTest
    @DisplayName("상품 이름이 빈 값이면 예외를 던진다.")
    @ValueSource(strings = {"\n", "", " ", "       "})
    void throws_exception_if_product_name_is_blank(String name) {
        // given

        // when //then
        Assertions.assertThatThrownBy(new ProductName(name)::isValid).isInstanceOf(BusinessException.class).hasMessage(PRODUCT_NAME_IS_EMPTY.getMessage());
    }

    @ParameterizedTest(name = "상품 이름에 빈칸 허용하도록 변경됨.")
    @DisplayName("상품 이름에 특수문자가 포함되면 예외를 던진다.")
    @ValueSource(strings = {"Iced Americano", "**(@(!"})
    @Disabled
    void throws_exception_if_product_name_is_invalid(String name) {
        // given

        // when //then
        Assertions.assertThatThrownBy(new ProductName(name)::isValid).isInstanceOf(BusinessException.class).hasMessage("빈칸 또는 특수문자는 사용 불가합니다.: [" + name + "]");
    }

    @DisplayName("상품 이름은 인스턴스와 상관없이 값으로 동등성을 비교한다.")
    @Test
    void product_name_equals() {
        // given
        ProductName name1 = new ProductName("만유");
        ProductName name2 = new ProductName("만유");
        // when
        Assertions.assertThat(name1.equals(name2)).isTrue();

        // then
    }
}