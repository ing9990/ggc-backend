package com.gigacoffeebackend.products.option.ui.response;

import com.gigacoffeebackend.products.option.domain.ProductOption;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductOptionResponse {

    private Long id;
    private String name;
    private BigDecimal price;

    public static ProductOptionResponse from(ProductOption option){
        return ProductOptionResponse.builder()
            .id(option.getId())
            .name(option.getName().toString())
            .price(option.getPrice().getValue())
            .build();
    }
}
