package com.gigacoffeebackend.category.ui;

import com.gigacoffeebackend.category.domain.Category;
import com.gigacoffeebackend.product.ui.ProductResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CategoryProductResponse {

    private String name;
    private Set<ProductResponse> products = new HashSet<>();

    @Builder
    private CategoryProductResponse(String name, Set<ProductResponse> products) {
        this.name = name;
        this.products = products;
    }

    public static CategoryProductResponse from(Category category) {
        toProductResponse(category);

        return CategoryProductResponse.builder()
                .name(category.getName())
                .products(toProductResponse(category))
                .build();
    }

    private static Set<ProductResponse> toProductResponse(Category category) {
        return category.getProducts()
                .stream().map(ProductResponse::from)
                .collect(Collectors.toSet());
    }
}
