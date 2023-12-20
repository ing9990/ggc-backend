package com.gigacoffeebackend.category.application;

import com.gigacoffeebackend.category.domain.Category;
import com.gigacoffeebackend.category.domain.CategoryService;
import com.gigacoffeebackend.category.ui.request.AddCategoryRequest;
import com.gigacoffeebackend.category.dto.CategoryNames;
import com.gigacoffeebackend.category.ui.response.CategoryProductResponse;
import com.gigacoffeebackend.category.ui.response.CategoryResponse;
import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.product.domain.ProductService;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreNotFoundException;
import com.gigacoffeebackend.store.domain.StoreService;
import com.gigacoffeebackend.store.ui.response.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.Optional.of;
import static org.springframework.data.util.Optionals.mapIfAllPresent;

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
        return findOrSaveCategory(request, products, store);
    }

    @Transactional
    public StoreResponse deleteCategory(final Long storeId, final String categoryName) {
        final Store foundStore = storeService.findStoreById(storeId)
            .orElseThrow(StoreNotFoundException::new);

        return mapIfAllPresent(
            of(foundStore),
            categoryService.findCategory(foundStore, categoryName),
            Store::deleteCategory)
            .map(StoreResponse::from)
            .orElseThrow(NoSuchElementException::new);
    }

    public CategoryNames findCategories(final Long storeId) {
        final Store foundStore = storeService.findStoreById(storeId)
            .orElseThrow(StoreNotFoundException::new);

        Set<String> categoryNames = foundStore.getCategories().stream().map(Category::getName)
            .collect(Collectors.toSet());

        return new CategoryNames().addAll(categoryNames);
    }

    public CategoryProductResponse findProducts(final Long storeId, final String categoryName) {
        final Store foundStore = storeService.findStoreById(storeId)
            .orElseThrow(StoreNotFoundException::new);

        final Category category = categoryService.getCategory(foundStore, categoryName);

        return CategoryProductResponse.from(category);
    }

    private CategoryResponse findOrSaveCategory(final AddCategoryRequest request,
        final Set<Product> products, final Store store) {
        final Category category = categoryService.saveOrFind(store, request.getName(),
            request.getDisplayName(), products);
        store.addCategory(category);
        return CategoryResponse.from(category);
    }
}
