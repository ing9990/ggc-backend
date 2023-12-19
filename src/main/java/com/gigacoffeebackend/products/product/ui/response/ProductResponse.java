package com.gigacoffeebackend.products.product.ui.response;

import com.gigacoffeebackend.products.product.domain.Product;

import lombok.*;

import java.math.BigDecimal;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
@Builder
public class ProductResponse {

    private Long productId;
    private String productName;
    private BigDecimal productPrice;

    public static ProductResponse from(Product product) {
        return ProductResponse.builder()
            .productId(product.getId())
            .productName(product.getName().toString())
            .productPrice(product.getPrice().getValue())
            .build();
    }
}