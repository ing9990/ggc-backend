package com.gigacoffeebackend.product.ui;

import com.gigacoffeebackend.product.domain.Product;

import lombok.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder
public class ProductResponse {

    private Long productId;
    private String productName;
    private int productPrice;

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
                .productId(product.getId())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .build();
    }
}
