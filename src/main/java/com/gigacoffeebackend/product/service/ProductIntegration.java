package com.gigacoffeebackend.product.service;

import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.product.domain.ProductService;
import com.gigacoffeebackend.product.ui.AddProductRequest;
import com.gigacoffeebackend.product.ui.ProductResponse;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreNotFoundException;
import com.gigacoffeebackend.store.domain.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.STORE_NOT_FOUND_AT_ADD_PRODUCT;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductIntegration {

    private final ProductService productService;
    private final StoreService storeService;

    @Transactional
    public ProductResponse addProduct(Long storeId, AddProductRequest addProductRequest) {
        Store foundStore = storeService.findStoreById(storeId)
                .orElseThrow(() -> new StoreNotFoundException(STORE_NOT_FOUND_AT_ADD_PRODUCT));
        Product product = productService.saveProduct(foundStore, addProductRequest.getProductName(), addProductRequest.getProductPrice());
        foundStore.addProduct(product);
        return ProductResponse.from(product);
    }
}
