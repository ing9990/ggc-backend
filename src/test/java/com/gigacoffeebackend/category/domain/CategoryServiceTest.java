package com.gigacoffeebackend.category.domain;

import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.product.domain.ProductRepository;
import com.gigacoffeebackend.product.domain.ProductName;
import com.gigacoffeebackend.product.domain.ProductPrice;
import com.gigacoffeebackend.store.domain.LocationName;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreName;
import com.gigacoffeebackend.store.domain.StoreRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class CategoryServiceTest {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
    }

    @DisplayName("등록된 카테고리가 없다면 카테고리를 생성하고 상품을 추가한다.")
    @Test
    void make_category_and_add_product_if_not_exist() {
        // given
        String name = "coffee";
        String displayName = "커피";

        Store store = 스토어_저장됨("메가커피", "합정역점");

        // when
        Category category = categoryService.saveOrFind(store, name, displayName, Set.of());

        // then
        assertThat(category.getName()).isEqualTo("coffee");
        assertThat(category.getDisplayName()).isEqualTo("커피");
        assertThat(category.getProducts()).isEqualTo(Set.of());
    }

    @DisplayName("등록된 카테고리가 있다면 카테고리를 찾아 상품을 추가한다.")
    @Test
    void add_product_to_category_if_exists() {
        // given
        String name = "coffee";
        String displayName = "커피";
        Store store = 스토어_저장됨("메가커피", "합정역점");

        Product product1 = 상품이_저장됨(store, "아메리카노", 2000);
        Product product2 = 상품이_저장됨(store, "아이스티", 1500);
        Product product3 = 상품이_저장됨(store, "레몬에이드", 3500);

        Set<Product> productSet = Set.of(product1, product2);
        Category savedCategory = 카테고리_생성됨(store, name, displayName, productSet);
        categoryRepository.save(savedCategory);

        // when
        Category category = categoryService.saveOrFind(store, name, displayName, Set.of(product3));

        // then
        assertThat(category.getName()).isEqualTo("coffee");
        assertThat(category.getDisplayName()).isEqualTo("커피");

        assertThat(category.getProducts())
                .hasSize(3)
                .extracting("name", "price")
                .containsExactlyInAnyOrder(
                        tuple("아메리카노", 2000),
                        tuple("아이스티", 1500),
                        tuple("레몬에이드", 3500)
                );
    }

    private Product 상품이_저장됨(Store store, String name, int price) {
        return productRepository.save(Product.makeProductWith(store, new ProductName(name), new ProductPrice(price)));
    }

    private Category 카테고리_생성됨(Store store, String name, String displayName, Set<Product> products) {
        return Category.makeCategory(store, name, displayName, products);
    }


    private Store 스토어_저장됨(String name, String locationName) {
        return storeRepository.save(Store.makeStore(new StoreName(name), new LocationName(locationName)));
    }

}