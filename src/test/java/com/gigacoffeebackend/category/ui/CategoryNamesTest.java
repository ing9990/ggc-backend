package com.gigacoffeebackend.category.ui;

import com.gigacoffeebackend.category.dto.CategoryNames;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
class CategoryNamesTest {

    @DisplayName("CategoryNames에 추가된 이름을 찾을 수 있다.")
    @Test
    void find() {
        // given
        CategoryNames categoryNames = new CategoryNames();
        List<String> names = List.of("카테고리1", "카테고리2", "카테고리3");
        categoryNames.addAll(names);

        // when
        Optional<String> foundName = categoryNames.find("카테고리2");

        // then
        assertThat(foundName).isPresent();
        assertThat(foundName).contains("카테고리2");
    }

    @DisplayName("CategoryNams에 카테고리 이름을 추가할 수 있다.")
    @Test
    void addAll() {
        CategoryNames categoryNames = new CategoryNames();
        List<String> names = List.of("카테고리1", "카테고리2", "카테고리3");

        //when
        CategoryNames changed = categoryNames.addAll(names);

        // then
        Assertions.assertThat(changed).isNotNull();
        Assertions.assertThat(changed.getCategories()).hasSize(3);
    }
}