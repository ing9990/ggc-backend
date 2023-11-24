package com.gigacoffeebackend.category.application;

import com.gigacoffeebackend.category.dto.CategoryNames;
import com.gigacoffeebackend.category.ui.AddCategoryRequest;
import com.gigacoffeebackend.category.ui.CategoryResponse;
import com.gigacoffeebackend.store.domain.LocationName;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreName;
import com.gigacoffeebackend.store.domain.StoreRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CategoryIntegrationTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CategoryIntegration categoryIntegration;


    @DisplayName("카테고리를 추가할 수 있다.")
    @Test
    void make_category_with_request() {
        // given
        String name = "메가커피";
        String locationName = "합정역점";

        Store store = 스토어_저장됨(name, locationName);
        Set<Long> products = Set.of();
        AddCategoryRequest request = new AddCategoryRequest("coffee", "커피", products);
        Long storeId = store.getId();

        // when
        CategoryResponse categoryResponse = categoryIntegration.addCategory(storeId, request);

        // then
        assertThat(categoryResponse).isNotNull();
        assertThat(categoryResponse.getName()).isEqualTo("coffee");
        assertThat(categoryResponse.getProducts()).isEmpty();
    }

    @DisplayName("스토어 아이디로 카테고리들을 가져올 수 있다.")
    @Test
    void get_categories_with_store_id() {
        // given
        String name = "메가커피";
        String locationName = "합정역점";

        Store store = 스토어_저장됨(name, locationName);
        Set<Long> products = Set.of();
        AddCategoryRequest request = new AddCategoryRequest("coffee", "커피", products);
        Long storeId = store.getId();
        CategoryResponse categoryResponse = categoryIntegration.addCategory(storeId, request);

        // when
        CategoryNames categories = categoryIntegration.findCategories(storeId);

        // then
        assertThat(categories).isNotNull();
        assertThat(categories.getCategories()).isNotNull();
        assertThat(categories.getCategories()).hasSize(1);
        assertThat(categories.getCategories()).containsExactly("coffee");
    }

    private Store 스토어_저장됨(String name, String locationName) {
        return storeRepository.save(Store.makeStore(new StoreName(name), new LocationName(locationName)));
    }
}