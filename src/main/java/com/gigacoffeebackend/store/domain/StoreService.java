package com.gigacoffeebackend.store.domain;

import com.gigacoffeebackend.category.domain.Category;
import com.gigacoffeebackend.category.domain.CategoryRepository;
import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.product.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.STORE_DUPLICATED;
import static com.gigacoffeebackend.global.exceptions.ErrorCode.STORE_DUPLICATED_ON_UPDATE;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;
    private final CategoryRepository categoryRepository;

    @Transactional
    public Store saveStoreWithDefault(final StoreName name, final LocationName locationName) {
        checkStoreDuplicate(name, locationName);

        Store store = storeRepository.save(Store.makeStore(name, locationName));
        categoryRepository.save(Category.makeDefault(store));

        return store;
    }

    @Transactional
    public void updateStore(final Store foundStore, final StoreName storeName, final LocationName locationName) {
        try {
            checkStoreDuplicate(storeName, locationName);
        } catch (BusinessException e) {
            throw new BusinessException(STORE_DUPLICATED_ON_UPDATE);
        }

        foundStore.updateNameAndLocationName(storeName, locationName);
    }

    @Transactional
    public void addProductToStore(final Store foundStore, final Product product) {
        foundStore.addProduct(product);
    }

    public Optional<Store> findStoreById(final Long storeId) {
        return storeRepository.findById(storeId);
    }

    private void checkStoreDuplicate(final StoreName name, final LocationName locationName) {
        if (storeRepository.existsStoreByNameAndLocationName(name, locationName)) {
            throw new BusinessException(STORE_DUPLICATED);
        }
    }
}
