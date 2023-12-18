package com.gigacoffeebackend.store.application;

import com.gigacoffeebackend.category.ui.CategoryResponse;
import com.gigacoffeebackend.store.domain.LocationName;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreName;
import com.gigacoffeebackend.store.domain.StoreNotFoundException;
import com.gigacoffeebackend.store.domain.StoreService;
import com.gigacoffeebackend.store.ui.AddStoreRequest;
import com.gigacoffeebackend.store.ui.StoreResponse;
import com.gigacoffeebackend.store.ui.TotalStoreResponse;
import com.gigacoffeebackend.store.ui.UpdateStoreRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.STORE_NOT_FOUND_ON_UPDATE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreIntegration {

    private final StoreService storeService;

    @Transactional
    public StoreResponse addStore(AddStoreRequest addStoreRequest) {
        Store savedStore = storeService.saveStore(new StoreName(addStoreRequest.getName()), new LocationName(addStoreRequest.getLocation()));

        return StoreResponse.from(savedStore);
    }

    @Transactional
    public TotalStoreResponse updateStore(final Long storeId, UpdateStoreRequest updateStoreRequest) {
        Store foundStore = storeService.findStoreById(storeId).orElseThrow(() -> new StoreNotFoundException(STORE_NOT_FOUND_ON_UPDATE));

        storeService.updateStore(foundStore, updateStoreRequest.toStoreName(), updateStoreRequest.toLocation());

        return findStore(foundStore.getId());
    }

    public TotalStoreResponse findStore(final Long storeId) {
        final Store foundStore = storeService.findStoreById(storeId).orElseThrow(StoreNotFoundException::new);

        Set<CategoryResponse> categories = foundStore.getCategories().stream().map(CategoryResponse::from).collect(Collectors.toSet());

        return TotalStoreResponse.from(foundStore, categories);
    }
}
