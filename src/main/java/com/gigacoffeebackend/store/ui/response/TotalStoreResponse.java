package com.gigacoffeebackend.store.ui.response;

import com.gigacoffeebackend.category.ui.response.CategoryResponse;
import com.gigacoffeebackend.store.domain.Store;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@NoArgsConstructor
public class TotalStoreResponse {

    private Long storeId;
    private String name;
    private Set<CategoryResponse> categories = new HashSet<>();

    @Builder
    private TotalStoreResponse(Long storeId, String name, Set<CategoryResponse> categories) {
        this.storeId = storeId;
        this.name = name;
        this.categories = categories;
    }

    public static TotalStoreResponse from(Store foundStore, Set<CategoryResponse> categories) {
        return new TotalStoreResponse(foundStore.getId(), foundStore.getFullName(), categories);
    }
}
