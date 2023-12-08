package com.gigacoffeebackend.history.domain;

import com.gigacoffeebackend.history.ui.StoreHistoriesResponse;
import com.gigacoffeebackend.product.application.ProductIntegration;
import com.gigacoffeebackend.product.ui.AddProductRequest;
import com.gigacoffeebackend.product.ui.ProductResponse;
import com.gigacoffeebackend.steps.ProductSteps;
import com.gigacoffeebackend.steps.StoreSteps;
import com.gigacoffeebackend.store.application.StoreIntegration;
import com.gigacoffeebackend.store.ui.AddStoreRequest;
import com.gigacoffeebackend.store.ui.StoreResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ProductHistoryServiceTest {

    @Autowired
    private StoreIntegration storeIntegration;

    @Autowired
    private ProductIntegration productIntegration;

    @Autowired
    private ProductHistoryService productHistoryService;

    @Autowired
    private ProductHistoryRepository productHistoryRepository;

    @BeforeEach
    void setUp() {
        productHistoryRepository.deleteAll();
    }

    @DisplayName("상품이 추가되면 히스토리 정보가 추가된다.")
    @Test
    void add_product() {
        // given
        AddStoreRequest addStoreRequest = StoreSteps.스토어등록요청_생성("스프링커피", "부평역점");
        AddProductRequest addProductRequest = ProductSteps.상품등록요청_생성("아이스커피", 5000, "all");
        AddProductRequest addProductRequest2 = ProductSteps.상품등록요청_생성("초코라떼", 6500, "all");
        AddProductRequest addProductRequest3 = ProductSteps.상품등록요청_생성("아메리카노", 4500, "all");

        StoreResponse storeResponse = storeIntegration.addStore(addStoreRequest);
        ProductResponse productResponse = productIntegration.addProduct(storeResponse.getStoreId(),
            addProductRequest);
        ProductResponse productResponse2 = productIntegration.addProduct(storeResponse.getStoreId(),
            addProductRequest2);
        ProductResponse productResponse3 = productIntegration.addProduct(storeResponse.getStoreId(),
            addProductRequest3);

        // when
        StoreHistoriesResponse histories = productHistoryService.findHistoriesByStoreId(
            storeResponse.getStoreId());

        // then
        assertThat(histories).isNotNull();
        assertThat(histories.getHistories()).hasSize(3);
        assertThat(histories.getHistories())
            .extracting("storeName", "locationName", "productName", "productPrice")
            .containsExactlyInAnyOrder(
                tuple("스프링커피", "부평역점", "아이스커피", BigDecimal.valueOf(5000)),
                tuple("스프링커피", "부평역점", "초코라떼", BigDecimal.valueOf(6500)),
                tuple("스프링커피", "부평역점", "아메리카노", BigDecimal.valueOf(4500))
            );
    }


}