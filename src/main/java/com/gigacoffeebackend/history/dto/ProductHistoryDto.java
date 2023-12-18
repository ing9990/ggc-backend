package com.gigacoffeebackend.history.dto;

import com.gigacoffeebackend.product.domain.ProductEventSource;
import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.product.domain.ProductName;
import com.gigacoffeebackend.product.domain.ProductPrice;
import com.gigacoffeebackend.store.domain.LocationName;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreName;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Builder
@Getter
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PRIVATE)
public class ProductHistoryDto {

    private Long storeId;
    private StoreName storeName;
    private LocationName locationName;
    private ProductName productName;
    private ProductPrice productPrice;

    public static ProductHistoryDto from(Store store, Product product) {
        return ProductHistoryDto.builder()
            .storeId(store.getId())
            .storeName(store.getName())
            .locationName(store.getLocationName())
            .productName(product.getName())
            .productPrice(product.getPrice())
            .build();
    }

    public static ProductHistoryDto from(ProductEventSource source) {
        return ProductHistoryDto.builder()
            .storeId(source.getStoreId())
            .storeName(new StoreName(source.getStoreName()))
            .locationName(new LocationName(source.getLocationName()))
            .productName(new ProductName(source.getProductName()))
            .productPrice(new ProductPrice(source.getProductPrice()))
            .build();
    }
}
