package com.gigacoffeebackend.product.domain;

import static com.gigacoffeebackend.product.domain.ProductEventType.CREATED;
import static com.gigacoffeebackend.product.domain.ProductEventType.DELETED;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductEventSource {

    private Long storeId;
    private String storeName;
    private String locationName;
    private String productName;
    private BigDecimal productPrice;
    private ProductEventType type;

    public static ProductEventSource fromSaved(Product product) {
        return ProductEventSource.builder()
            .storeId(product.getStore().getId())
            .storeName(product.getStore().getName().toString())
            .locationName(product.getStore().getLocationName().toString())
            .productName(product.getName().toString())
            .productPrice(product.getPrice().getValue())
            .type(CREATED)
            .build();
    }

    public static ProductEventSource fromDeleted(Product product) {
        return ProductEventSource.builder()
            .storeId(product.getStore().getId())
            .storeName(product.getStore().getName().toString())
            .locationName(product.getStore().getLocationName().toString())
            .productName(product.getName().toString())
            .productPrice(product.getPrice().getValue())
            .type(DELETED)
            .build();
    }
}
