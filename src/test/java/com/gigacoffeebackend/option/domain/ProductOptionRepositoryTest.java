package com.gigacoffeebackend.option.domain;


import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.product.domain.ProductName;
import com.gigacoffeebackend.product.domain.ProductPrice;
import com.gigacoffeebackend.product.domain.ProductRepository;
import com.gigacoffeebackend.store.domain.LocationName;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreName;
import com.gigacoffeebackend.store.domain.StoreRepository;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ProductOptionRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;


    @DisplayName("상품 옵션을 추가하면 상품을 조회할 때 추가된 옵션이 함께 조회된다.")
    @Test
    void add_option() {
        // given
        Product product = 상품_저장됨();
        ProductOption option = ProductOption.makeOption(product, new OptionName("샷 추가"), new OptionPrice(500));
        ProductOption savedOption = productOptionRepository.save(option);

        // when
        Set<ProductOption> options = savedOption.getProduct().getOptions();

        // then
        assertThat(options)
            .extracting("name", "price", "visible")
            .containsExactlyInAnyOrder(
                tuple(
                    new OptionName("샷 추가"), new OptionPrice(500), ProductOptionVisible.VISIBLE)
            );
    }

    private Product 상품_저장됨() {
        Store store = Store.makeStore(new StoreName("만유인력"), new LocationName("합정역점"));
        storeRepository.save(store);

        Product product = Product.makeProductWith(store, new ProductName("아메리카노"), new ProductPrice(4500));
        return productRepository.save(product);
    }


}