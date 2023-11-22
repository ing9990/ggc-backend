package com.gigacoffeebackend.store.application;

import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreService;
import com.gigacoffeebackend.store.ui.AddStoreRequest;
import com.gigacoffeebackend.store.ui.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreIntegration {

    private final StoreService storeService;

    @Transactional
    public StoreResponse addStore(AddStoreRequest addStoreRequest) {
        Store savedStore = storeService.saveStore(addStoreRequest.getName(), addStoreRequest.getLocation());
        return StoreResponse.from(savedStore);
    }
}
