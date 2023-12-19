package com.gigacoffeebackend.products.product.application;

import com.gigacoffeebackend.category.domain.Category;
import com.gigacoffeebackend.category.domain.CategoryService;
import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.products.option.ui.response.ProductAndOptionsResponse;
import com.gigacoffeebackend.products.product.domain.Product;
import com.gigacoffeebackend.products.product.domain.ProductName;
import com.gigacoffeebackend.products.product.domain.ProductNotFoundException;
import com.gigacoffeebackend.products.product.domain.ProductPrice;
import com.gigacoffeebackend.products.product.domain.ProductService;
import com.gigacoffeebackend.products.product.ui.request.AddProductRequest;
import com.gigacoffeebackend.products.product.ui.response.ProductResponse;
import com.gigacoffeebackend.products.product.ui.response.StoreProductsResponse;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreNotFoundException;
import com.gigacoffeebackend.store.domain.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.*;
import static java.util.Optional.of;
import static org.springframework.data.util.Optionals.ifAllPresent;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductIntegration {

    private final ProductService productService;
    private final StoreService storeService;
    private final CategoryService categoryService;

    @Transactional
    public ProductResponse addProduct(final Long storeId,
        final AddProductRequest addProductRequest) {
        final Store foundStore = storeService.findStoreById(storeId)
            .orElseThrow(() -> new StoreNotFoundException(STORE_NOT_FOUND_ON_ADD_PRODUCT));
        final Product product = productService.saveProduct(foundStore,
            new ProductName(addProductRequest.getProductName()),
            new ProductPrice(addProductRequest.getProductPrice()));

        storeService.addProductToStore(foundStore, product);
        addCategoryToProductIfPresent(addProductRequest, foundStore, product);
        return ProductResponse.from(product);
    }

    @Transactional
    public StoreProductsResponse deleteProduct(Long storeId, Long productId) {
        final Store foundStore = storeService.findStoreById(storeId)
            .orElseThrow(() -> new StoreNotFoundException(STORE_NOT_FOUND));
        ifAllPresent(of(foundStore),
            productService.findProductByStoreAndProductId(foundStore, productId),
            storeService::deleteProduct);
        return StoreProductsResponse.of(foundStore);
    }

    public StoreProductsResponse findStore(final Long storeId) {
        final Store foundStore = storeService.findStoreById(storeId)
            .orElseThrow(() -> new StoreNotFoundException(STORE_NOT_FOUND_ON_ADD_PRODUCT));
        final Set<Product> products = foundStore.getProducts();
        return StoreProductsResponse.of(foundStore, products);
    }

    public ProductAndOptionsResponse findProduct(Long storeId, Long productId) {
        final Store foundStore = storeService.findStoreById(storeId)
            .orElseThrow(StoreNotFoundException::new);

        final Product foundProduct = productService.findProductByStoreAndProductId(foundStore, productId)
            .orElseThrow(ProductNotFoundException::new);

        return ProductAndOptionsResponse.from(foundProduct);
    }

    private void addCategoryToProductIfPresent(AddProductRequest addProductRequest,
        Store foundStore, Product product) {
        Optional<Category> category = categoryService.findCategory(foundStore,
            addProductRequest.getCategoryName());
        if (category.isEmpty()) {
            throw new BusinessException(CATEGORY_NOT_FOUND_IN_STORE);
        }
        category.ifPresent(value -> productService.addCategoryToProduct(product, value));
        category.ifPresent(value -> categoryService.saveProductToCategory(product, value));
    }
}
