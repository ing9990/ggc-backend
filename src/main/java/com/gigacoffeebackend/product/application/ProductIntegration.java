package com.gigacoffeebackend.product.application;

import com.gigacoffeebackend.category.domain.Category;
import com.gigacoffeebackend.category.domain.CategoryService;
import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.product.domain.ProductName;
import com.gigacoffeebackend.product.domain.ProductPrice;
import com.gigacoffeebackend.product.domain.ProductService;
import com.gigacoffeebackend.product.ui.AddProductRequest;
import com.gigacoffeebackend.product.ui.ProductResponse;
import com.gigacoffeebackend.product.ui.StoreProductsResponse;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreNotFoundException;
import com.gigacoffeebackend.store.domain.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.CATEGORY_NOT_FOUND_IN_STORE;
import static com.gigacoffeebackend.global.exceptions.ErrorCode.STORE_NOT_FOUND_AT_ADD_PRODUCT;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductIntegration {

    private final ProductService productService;
    private final StoreService storeService;
    private final CategoryService categoryService;

    @Transactional
    public ProductResponse addProduct(final Long storeId, final AddProductRequest addProductRequest) {
        final Store foundStore = storeService.findStoreById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(STORE_NOT_FOUND_AT_ADD_PRODUCT));

        final Product product = productService.saveProduct(foundStore,
                new ProductName(addProductRequest.getProductName()),
                new ProductPrice(addProductRequest.getProductPrice()));

        storeService.addProductToStore(foundStore, product);
        addCategoryToProductIfPresent(addProductRequest, foundStore, product);
        return ProductResponse.from(product);
    }

    public StoreProductsResponse findStore(final Long storeId) {
        final Store foundStore = storeService.findStoreById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(STORE_NOT_FOUND_AT_ADD_PRODUCT));

        final Set<Product> products = foundStore.getProducts();
        return StoreProductsResponse.of(foundStore, products);
    }

    private void addCategoryToProductIfPresent(AddProductRequest addProductRequest, Store foundStore, Product product) {
        Optional<Category> category = categoryService.findCategory(foundStore, addProductRequest.getCategoryName());
        if (category.isEmpty()) {
            throw new BusinessException(CATEGORY_NOT_FOUND_IN_STORE);
        }

        category.ifPresent(value -> productService.addCategoryToProduct(product, value));
        category.ifPresent(value -> categoryService.saveProductToCategory(product, value));
    }
}
