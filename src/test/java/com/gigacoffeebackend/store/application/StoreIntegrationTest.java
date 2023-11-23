package com.gigacoffeebackend.store.application;

import com.gigacoffeebackend.category.application.CategoryIntegration;
import com.gigacoffeebackend.category.ui.AddCategoryRequest;
import com.gigacoffeebackend.category.ui.CategoryResponse;
import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.product.application.ProductIntegration;
import com.gigacoffeebackend.product.ui.AddProductRequest;
import com.gigacoffeebackend.product.ui.ProductResponse;
import com.gigacoffeebackend.steps.CategorySteps;
import com.gigacoffeebackend.steps.ProductSteps;
import com.gigacoffeebackend.steps.StoreSteps;
import com.gigacoffeebackend.store.domain.StoreService;
import com.gigacoffeebackend.store.ui.AddStoreRequest;
import com.gigacoffeebackend.store.ui.StoreResponse;
import com.gigacoffeebackend.store.ui.TotalStoreResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StoreIntegrationTest {

    @Autowired
    private StoreIntegration storeIntegration;

    @Autowired
    private ProductIntegration productIntegration;

    @Autowired
    private CategoryIntegration categoryIntegration;

    @Autowired
    private StoreService storeService;

    @DisplayName("새로운 스토어를 생성한다.")
    @Test
    void create_store() {
        // given
        AddStoreRequest addStoreRequest = getAddStoreRequest("메가커피", "합정역점");

        // when
        StoreResponse storeResponse = storeIntegration.addStore(addStoreRequest);

        // then
        Assertions.assertThat(storeResponse.getStoreFullName()).isEqualTo("메가커피 합정역점");
    }

    @DisplayName("스토어의 이름과 지역이 모두 같을 수 없다.")
    @Test
    void store_name_and_location_cannot_duplicate() {
        // given
        AddStoreRequest addStoreRequest = getAddStoreRequest("메가커피", "합정역점");
        storeIntegration.addStore(addStoreRequest);

        // when then
        Assertions.assertThatThrownBy(() -> storeIntegration.addStore(addStoreRequest))
                .isInstanceOf(BusinessException.class);
    }

    @DisplayName("스토어의 정보와 각 스토어에 생성된 카테고리, 카테고리에 담긴 음료들을 조회할 수 있다.")
    @Test
    void find_store_info_and_categories() {
        // given
        AddStoreRequest request = StoreSteps.스토어등록요청_생성("메가커피", "부평역점");
        StoreResponse storeResponse = storeIntegration.addStore(request);
        Long storeId = storeResponse.getStoreId();

        AddCategoryRequest addCategoryRequest = CategorySteps.카테고리생성_요청("icedcoffee", "아이스 커피", Set.of());
        CategoryResponse categoryResponse = categoryIntegration.addCategory(storeId, addCategoryRequest);

        AddProductRequest productRequest1 = ProductSteps.상품등록요청_생성("개쩌는 아이스 아메리카노", 1500, "icedcoffee");
        ProductResponse productResponse1 = productIntegration.addProduct(storeId, productRequest1);
        AddProductRequest productRequest2 = ProductSteps.상품등록요청_생성("개쩌는 아메리카노", 2000, "icedcoffee");
        ProductResponse productResponse2 = productIntegration.addProduct(storeId, productRequest2);
        AddProductRequest productRequest3 = ProductSteps.상품등록요청_생성("개쩌는 바닐라 아메리카노", 2500, "icedcoffee");
        ProductResponse productResponse3 = productIntegration.addProduct(storeId, productRequest3);

        // when
        TotalStoreResponse totalStoreResponse = storeIntegration.findStore(storeId);

        // then
        Assertions.assertThat(totalStoreResponse.getStoreId()).isEqualTo(storeId);
        Assertions.assertThat(totalStoreResponse.getName()).isEqualTo("메가커피 부평역점");
        Assertions.assertThat(totalStoreResponse.getCategories()).hasSize(1);
        Assertions.assertThat(totalStoreResponse.getCategories())
                .extracting("name", "displayName")
                .containsExactlyInAnyOrder(
                        tuple("icedcoffee", "아이스 커피")
                );
    }


    private AddStoreRequest getAddStoreRequest(String name, String locationName) {
        return new AddStoreRequest(name, locationName);
    }
}