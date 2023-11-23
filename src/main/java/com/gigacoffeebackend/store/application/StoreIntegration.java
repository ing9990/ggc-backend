package com.gigacoffeebackend.store.application;

import com.gigacoffeebackend.category.ui.CategoryResponse;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreNotFoundException;
import com.gigacoffeebackend.store.domain.StoreService;
import com.gigacoffeebackend.store.ui.AddStoreRequest;
import com.gigacoffeebackend.store.ui.StoreResponse;
import com.gigacoffeebackend.store.ui.TotalStoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreIntegration {

    private final StoreService storeService;

    @Transactional
    public StoreResponse addStore(AddStoreRequest addStoreRequest) {
        Store savedStore = storeService.saveStoreWithDefault(addStoreRequest.getName(), addStoreRequest.getLocation());
        return StoreResponse.from(savedStore);
    }

    public TotalStoreResponse findStore(Long storeId) {
        final Store foundStore = storeService.findStoreById(storeId)
                .orElseThrow(StoreNotFoundException::new);

        Set<CategoryResponse> categories = foundStore.getStoreCategory()
                .stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toSet());

        return TotalStoreResponse.from(foundStore, categories);
    }
}
