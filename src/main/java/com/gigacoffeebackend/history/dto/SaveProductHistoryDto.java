package com.gigacoffeebackend.history.dto;

import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.product.domain.ProductName;
import com.gigacoffeebackend.product.domain.ProductPrice;
import com.gigacoffeebackend.store.domain.LocationName;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreName;
import lombok.*;

import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Getter
@NoArgsConstructor(access = PROTECTED)
@Builder
@AllArgsConstructor(access = PRIVATE)
public class SaveProductHistoryDto {

    private Long storeId;

    private StoreName storeName;

    private LocationName locationName;

    private ProductName productName;

    private ProductPrice productPrice;

    public static SaveProductHistoryDto from(Store store, Product product) {
        return SaveProductHistoryDto.builder()
                .storeId(store.getId())
                .storeName(store.getName())
                .locationName(store.getLocationName())
                .productName(product.getName())
                .productPrice(product.getPrice())
                .build();
    }
}
