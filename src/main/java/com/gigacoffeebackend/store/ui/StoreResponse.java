package com.gigacoffeebackend.store.ui;

import com.gigacoffeebackend.store.domain.Store;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreResponse {

    private String storeFullName;

    public static StoreResponse from(Store store) {
        return StoreResponse.builder()
                .storeFullName(store.getFullName())
                .build();
    }
}
