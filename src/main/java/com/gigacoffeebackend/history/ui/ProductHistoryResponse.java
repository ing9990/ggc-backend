package com.gigacoffeebackend.history.ui;

import com.gigacoffeebackend.history.domain.ProductHistoryCollection;
import lombok.Getter;

import java.time.LocalDateTime;


@Getter
public class ProductHistoryResponse {

    private String id;

    private String storeName;

    private String locationName;

    private String productName;

    private int productPrice;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ProductHistoryResponse(String storeName, String locationName, String productName, int productPrice, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.storeName = storeName;
        this.locationName = locationName;
        this.productName = productName;
        this.productPrice = productPrice;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public ProductHistoryResponse() {
    }

    public static ProductHistoryResponse from(ProductHistoryCollection productHistory) {
        return new ProductHistoryResponse(
                productHistory.getStoreName(),
                productHistory.getLocationName(),
                productHistory.getProductName(),
                productHistory.getProductPrice(),
                productHistory.getCreatedAt(),
                productHistory.getUpdatedAt());
    }
}
