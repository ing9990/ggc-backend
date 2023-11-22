package com.gigacoffeebackend.category.domain;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.store.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.CATEGORY_NOT_FOUND_IN_STORE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional
    public Category saveOrFind(final Store store, final String name, final String displayName, final Set<Product> products) {
        return categoryRepository.findCategoryByStoreAndName(store, name)
                .map(category -> category.addProducts(products))
                .orElseGet(() -> save(store, name, displayName, products));
    }

    public Category getCategory(final Store store, final String name) {
        return categoryRepository.findCategoryByStoreAndName(store, name)
                .orElseThrow(() -> new BusinessException(CATEGORY_NOT_FOUND_IN_STORE));
    }

    public Optional<Category> findCategory(final Store store, final String name) {
        return categoryRepository.findCategoryByStoreAndName(store, name);
    }


    private Category save(final Store store, final String name, final String displayName, final Set<Product> products) {
        return categoryRepository.save(Category.makeCategory(store, name, displayName, products));
    }
}
