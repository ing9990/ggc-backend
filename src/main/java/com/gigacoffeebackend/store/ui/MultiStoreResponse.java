package com.gigacoffeebackend.store.ui;

import com.gigacoffeebackend.store.domain.Store;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MultiStoreResponse {

    private List<StoreResponse> stores;

    public static MultiStoreResponse fromStores(List<Store> stores) {
        return MultiStoreResponse.builder()
                .stores(stores.stream().map(StoreResponse::from)
                        .collect(Collectors.toList()))
                .build();
    }
}
