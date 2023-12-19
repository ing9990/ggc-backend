package com.gigacoffeebackend.products.option.domain;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.global.exceptions.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OptionNameTest {

    @ValueSource(strings = {"얼음 추가", "레몬시럽 추가", "매운맛추가 (매우매우 매움)"})
    @ParameterizedTest
    @DisplayName("옵션 이름은 문자, 숫자와 일부 특수문자만 허용한다.")
    void valid_option_name(String value) {
        assertDoesNotThrow(() -> new OptionName(value));
    }

    @ValueSource(strings = {"얼음 추가!@#", "레몬시럽 추가^%$", "매운맛추가^^9"})
    @ParameterizedTest
    @DisplayName("옵션 이름은 문자, 숫자와 일부 특수문자만 허용한다.")
    void invalid_option_name(String value) {
        assertThatThrownBy(() -> new OptionName(value))
            .isInstanceOf(BusinessException.class)
            .hasMessage(ErrorCode.INVALID_OPTION_NAME.getMessage());
    }
}