package com.gigacoffeebackend.product.ui;

import com.gigacoffeebackend.global.dto.ApiResponse;
import com.gigacoffeebackend.product.application.ProductIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.gigacoffeebackend.global.dto.ApiResponse.ok;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/stores/{storeId}/products")
@RequiredArgsConstructor
public class ProductApi {

    private final ProductIntegration productIntegration;

    @GetMapping
    ResponseEntity<ApiResponse<StoreProductsResponse>> products(
            @PathVariable Long storeId
    ) {
        final StoreProductsResponse response = productIntegration.findStore(storeId);
        return status(OK).body(ok(response));
    }

    @PostMapping
    ResponseEntity<ApiResponse<ProductResponse>> addProduct(
            @PathVariable Long storeId,
            @Valid @RequestBody AddProductRequest addProductRequest
    ) {
        final ProductResponse response = productIntegration.addProduct(storeId, addProductRequest);
        return status(OK).body(ok(response));
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<ApiResponse<StoreProductsResponse>> deleteProduct(
            @PathVariable Long storeId,
            @PathVariable Long productId
    ) {
        final StoreProductsResponse response = productIntegration.deleteProduct(storeId, productId);
        return status(OK).body(ok(response));
    }
}