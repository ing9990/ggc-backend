package com.gigacoffeebackend.category.service;

import com.gigacoffeebackend.category.domain.Category;
import com.gigacoffeebackend.category.domain.CategoryService;
import com.gigacoffeebackend.category.ui.AddCategoryRequest;
import com.gigacoffeebackend.category.dto.CategoryNames;
import com.gigacoffeebackend.category.ui.CategoryProductResponse;
import com.gigacoffeebackend.category.ui.CategoryResponse;
import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.product.domain.ProductService;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreNotFoundException;
import com.gigacoffeebackend.store.domain.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryIntegration {

    private final CategoryService categoryService;
    private final StoreService storeService;
    private final ProductService productService;

    @Transactional
    public CategoryResponse addCategory(final Long storeId, final AddCategoryRequest request) {
        final Store store = storeService.findStoreById(storeId)
                .orElseThrow(StoreNotFoundException::new);

        Set<Product> products = productService.findAllByIds(request.getProducts());
        Category category = findOrSaveCategory(request, products, store);
        return CategoryResponse.from(category);
    }

    public CategoryNames findCategories(final Long storeId) {
        final Store foundStore = storeService.findStoreById(storeId)
                .orElseThrow(StoreNotFoundException::new);

        Set<String> categoryNames = foundStore.getStoreCategory().stream().map(Category::getName)
                .collect(Collectors.toSet());

        return new CategoryNames().addAll(categoryNames);
    }

    /**
     * 카테고리가 없다면 새로 만들어 상품을 추가한다.
     * 카테고리가 있다면 카테고리를 찾아 상품을 추가한다.
     */
    private Category findOrSaveCategory(AddCategoryRequest request, Set<Product> products, Store store) {
        Category category = categoryService.saveOrFind(store, request.getName(), request.getDisplayName(), products);
        store.addCategory(category);
        return category;
    }


    public CategoryProductResponse findProducts(Long storeId, String categoryName) {
        final Store foundStore = storeService.findStoreById(storeId)
                .orElseThrow(StoreNotFoundException::new);

        final Category category = categoryService.findCategory(foundStore, categoryName);

        return CategoryProductResponse.from(category);
    }
}
