package com.gigacoffeebackend.category.ui.response;

import com.gigacoffeebackend.category.domain.Category;
import com.gigacoffeebackend.products.product.domain.Product;
import com.gigacoffeebackend.products.product.ui.response.ProductResponse;
import lombok.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor
public class CategoryResponse {

    private String name;

    private List<ProductResponse> products;

    @Builder
    private CategoryResponse(String name, Set<Product> products) {
        this.name = name;
        this.products = toProductResponse(products);
    }

    private List<ProductResponse> toProductResponse(Set<Product> products) {
        return products.stream().map(ProductResponse::from).collect(Collectors.toList());
    }

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getName(), category.getProducts());
    }
}
