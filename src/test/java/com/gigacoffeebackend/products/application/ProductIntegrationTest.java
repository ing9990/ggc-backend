package com.gigacoffeebackend.products.application;

import com.gigacoffeebackend.category.domain.Category;
import com.gigacoffeebackend.category.domain.CategoryRepository;
import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.product.application.ProductIntegration;
import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.product.domain.ProductName;
import com.gigacoffeebackend.product.domain.ProductRepository;
import com.gigacoffeebackend.product.ui.request.AddProductRequest;
import com.gigacoffeebackend.product.ui.response.ProductResponse;
import com.gigacoffeebackend.store.domain.LocationName;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreName;
import com.gigacoffeebackend.store.domain.StoreRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.PRODUCT_PRICE_IS_INVALID;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ProductIntegrationTest {

    @Autowired
    private ProductIntegration productIntegration;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @DisplayName("상품을 추가할 수 있다.")
    @Test
    void make_product_with_store() {
        // given
        Store store = Store.makeStore(new StoreName("안녕커피"), new LocationName("화곡역점"));
        storeRepository.save(store);

        Category category = Category.makeDefault(store);
        categoryRepository.save(category);

        AddProductRequest addProductRequest = new AddProductRequest("망고스무디", 4500, category.getName());

        // when
        ProductResponse productResponse = productIntegration.addProduct(store.getId(), addProductRequest);

        // then
        assertThat(productRepository.findAll().size()).isNotZero();
        Optional<Product> find = productRepository.findById(productResponse.getProductId());
        assertThat(find).isPresent();
        assertThat(find.get().getName()).isEqualTo(new ProductName("망고스무디"));
    }

    @DisplayName("상품을 추가할 때 가격은 100원 단위로 나누어 떨어진다.")
    @Test
    void product_price_rounded_to_100() {
        // given
        StoreName name = new StoreName("안녕커피");
        LocationName location = new LocationName("화곡역점");
        Store store = Store.makeStore(name, location);
        storeRepository.save(store);

        Category category = Category.makeDefault(store);
        categoryRepository.save(category);

        AddProductRequest addProductRequest = new AddProductRequest("망고스무디", 4530, category.getName());

        // when // then
        Assertions.
                assertThatThrownBy(() -> productIntegration.addProduct(store.getId(), addProductRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage(PRODUCT_PRICE_IS_INVALID.getMessage());
    }

    @DisplayName("상품을 추가할 때 가격은 음수가 될 수 없다.")
    @Test
    void product_price_is_positive() {
        // given
        StoreName name = new StoreName("안녕커피");
        LocationName location = new LocationName("화곡역점");
        Store store = Store.makeStore(name, location);
        storeRepository.save(store);

        Category category = Category.makeDefault(store);
        categoryRepository.save(category);

        AddProductRequest addProductRequest = new AddProductRequest("망고스무디", -20, category.getName());

        // when // then
        Assertions.assertThatThrownBy(() -> productIntegration.addProduct(store.getId(), addProductRequest))
                .isInstanceOf(BusinessException.class)
                .hasMessage(PRODUCT_PRICE_IS_INVALID.getMessage());

    }


}