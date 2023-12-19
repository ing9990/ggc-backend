package com.gigacoffeebackend.products.domain;

import com.gigacoffeebackend.products.product.domain.Product;
import com.gigacoffeebackend.products.product.domain.ProductName;
import com.gigacoffeebackend.products.product.domain.ProductPrice;
import com.gigacoffeebackend.store.domain.LocationName;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreName;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class ProductTest {

    @DisplayName("상품을 생성할 수 있다.")
    @Test
    void make_product() {
        // given
        Store store = Store.makeStore(new StoreName("xx커피"), new LocationName("합정역점"));

        // when
        Product product = Product.makeProductWith(store, new ProductName("시그니처커피"), new ProductPrice(4500));

        // then
        Assertions.assertThat(product.getName()).isEqualTo(new ProductName("시그니처커피"));
        Assertions.assertThat(product.getPrice()).isEqualTo(new ProductPrice(4500));
        Assertions.assertThat(product.getCategory()).isNull();
    }
}