package com.gigacoffeebackend.option.ui.response;

import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.product.ui.response.ProductResponse;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductAndOptionsResponse {

    private ProductResponse product;
    private List<ProductOptionResponse> options;

    public static ProductAndOptionsResponse from(final Product product) {
        return ProductAndOptionsResponse.builder()
            .product(ProductResponse.from(product))
            .options(toProductOptionResponse(product))
            .build();
    }

    private static List<ProductOptionResponse> toProductOptionResponse(Product product) {
        return product.getOptions().stream()
            .map(ProductOptionResponse::from)
            .collect(Collectors.toList());
    }
}
