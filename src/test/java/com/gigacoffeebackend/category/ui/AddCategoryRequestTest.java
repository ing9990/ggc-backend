package com.gigacoffeebackend.category.ui;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest
class AddCategoryRequestTest {

    @DisplayName("카테고리를 추가할 때 등록할 상품이 없다면 해당 카테고리의 상품에는 아무 것도 없다.")
    @Test
    void add_category_with_empty_product() {
        // given
        String name = "coffee";
        String displayName = "커피";
        Set<Long> products = null;

        // when
        AddCategoryRequest addCategoryRequest = new AddCategoryRequest(name, displayName, null);

        // then
        assertThat(addCategoryRequest.getProducts()).isNotNull();
        assertThat(addCategoryRequest.getProducts()).isEmpty();
        assertThat(addCategoryRequest.getName()).isEqualTo("coffee");
        assertThat(addCategoryRequest.getDisplayName()).isEqualTo("커피");
    }


    @ParameterizedTest
    @ValueSource(strings = {"카테고리", "한국어", "123456"})
    @DisplayName("카테고리의 이름은 영문만 허용한다.")
    @Disabled
    void make_category_with_korean(String categoryName) {
        // given
        Set<Long> products = Set.of();

        // when // then
        assertThatThrownBy(() -> new AddCategoryRequest(categoryName, "커피", products)).isInstanceOf(
            MethodArgumentNotValidException.class).hasMessage("영문만 입력해주세요.");
    }
}