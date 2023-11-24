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
    public void updateStore(Store foundStore, StoreName storeName, LocationName locationName) {
        checkStoreDuplicate(storeName, locationName);

        foundStore.updateNameAndLocationName(storeName, locationName);
    }

    @Transactional
    public void addProductToStore(Store foundStore, Product product) {
        foundStore.addProduct(product);
    }

    public Optional<Store> findStoreById(Long storeId) {
        return storeRepository.findById(storeId);
    }

    private void checkStoreDuplicate(StoreName name, LocationName locationName) {
        if (storeRepository.existsStoreByNameAndLocationName(name, locationName)) {
            throw new BusinessException(STORE_DUPLICATED);
        }
    }
}
