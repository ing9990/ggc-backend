package com.gigacoffeebackend.product.ui;

import java.util.Set;
import java.util.stream.Collectors;

import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.store.domain.Store;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreProductsResponse {

    private String storeName;
    private Set<ProductResponse> products;

    public static StoreProductsResponse of(Store foundStore, Set<Product> products) {
        return StoreProductsResponse.builder()
                .storeName(foundStore.getFullName())
                .products(toProductResponse(products))
                .build();
    }

    public static StoreProductsResponse of(Store foundStore) {
        return StoreProductsResponse.builder()
                .storeName(foundStore.getFullName())
                .products(toProductResponse(foundStore.getProducts()))
                .build();
    }

    private static Set<ProductResponse> toProductResponse(Set<Product> products) {
        return products.stream().map(ProductResponse::from)
                .collect(Collectors.toSet());
    }
}
