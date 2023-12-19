package com.gigacoffeebackend.store.ui.response;

import com.gigacoffeebackend.store.domain.Store;
import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreResponse {

    private Long storeId;
    private String storeFullName;

    public static StoreResponse from(Store store) {
        return StoreResponse.builder()
                .storeId(store.getId())
                .storeFullName(store.getFullName())
                .build();
    }
}
