package com.gigacoffeebackend.category.domain;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.global.exceptions.ErrorCode;
import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.store.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.CATEGORY_NOT_FOUND_IN_STORE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category saveOrFind(Store store, String name, String displayName, Set<Product> products) {
        return categoryRepository.findCategoryByStoreAndName(store, name)
                .map(category -> category.addProducts(products))
                .orElseGet(() -> save(store, name, displayName, products));
    }

    private Category save(Store store, String name, String displayName, Set<Product> products) {
        return categoryRepository.save(Category.makeCategory(store, name, displayName, products));
    }

    public Category findCategory(Store store, String name) {
        return categoryRepository.findCategoryByStoreAndName(store, name)
                .orElseThrow(() -> new BusinessException(CATEGORY_NOT_FOUND_IN_STORE));
    }
}
