package com.gigacoffeebackend.products.product.ui;

import com.gigacoffeebackend.global.dto.ApiResponse;
import com.gigacoffeebackend.products.option.ui.response.ProductAndOptionsResponse;
import com.gigacoffeebackend.products.product.application.ProductIntegration;
import com.gigacoffeebackend.products.product.ui.request.AddProductRequest;
import com.gigacoffeebackend.products.product.ui.response.ProductResponse;
import com.gigacoffeebackend.products.product.ui.response.StoreProductsResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.gigacoffeebackend.global.dto.ApiResponse.ok;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/stores/{storeId}/products")
@RequiredArgsConstructor
public class ProductApi {

    private final ProductIntegration productIntegration;

    @GetMapping("/{productId}")
    ResponseEntity<ApiResponse<ProductAndOptionsResponse>> product(
        @PathVariable Long productId,
        @PathVariable Long storeId
    ) {
        final ProductAndOptionsResponse response = productIntegration.findProduct(storeId, productId);
        return status(OK).body(ok(response));
    }

    @GetMapping
    ResponseEntity<ApiResponse<StoreProductsResponse>> products(@PathVariable Long storeId) {
        final StoreProductsResponse response = productIntegration.findStore(storeId);
        return status(OK).body(ok(response));
    }

    @PostMapping
    ResponseEntity<ApiResponse<ProductResponse>> addProduct(@PathVariable Long storeId, @Valid @RequestBody AddProductRequest addProductRequest) {
        final ProductResponse response = productIntegration.addProduct(storeId, addProductRequest);
        return status(CREATED).body(ok(response));
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<ApiResponse<StoreProductsResponse>> deleteProduct(@PathVariable Long storeId, @PathVariable Long productId) {
        final StoreProductsResponse response = productIntegration.deleteProduct(storeId, productId);
        return status(OK).body(ok(response));
    }
}