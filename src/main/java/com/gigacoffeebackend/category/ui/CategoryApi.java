package com.gigacoffeebackend.category.ui;

import com.gigacoffeebackend.category.dto.CategoryNames;
import com.gigacoffeebackend.category.application.CategoryIntegration;
import com.gigacoffeebackend.global.dto.ApiResponse;
import com.gigacoffeebackend.store.ui.StoreResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/stores/{storeId}/categories")
@RequiredArgsConstructor
public class CategoryApi {

    private final CategoryIntegration categoryIntegration;

    @PostMapping
    ResponseEntity<ApiResponse<CategoryResponse>> addCategory(
            @PathVariable Long storeId,
            @Valid @RequestBody AddCategoryRequest request
    ) {
        final CategoryResponse response = categoryIntegration.addCategory(storeId, request);

        return ok(ApiResponse.ok(response));
    }

    @GetMapping
    ResponseEntity<ApiResponse<CategoryNames>> categories(@PathVariable Long storeId) {
        final CategoryNames response = categoryIntegration.findCategories(storeId);

        return ok(ApiResponse.ok(response));
    }

    @GetMapping("/{categoryName}/products")
    ResponseEntity<ApiResponse<CategoryProductResponse>> categorieProducts(@PathVariable Long storeId, @PathVariable String categoryName) {
        final CategoryProductResponse response = categoryIntegration.findProducts(storeId, categoryName);

        return ok(ApiResponse.ok(response));
    }

    @DeleteMapping("/{categoryName}")
    ResponseEntity<ApiResponse<StoreResponse>> deleteCategory(@PathVariable Long storeId, @PathVariable String categoryName) {
        final StoreResponse response = categoryIntegration.deleteCategory(storeId, categoryName);

        return ok(ApiResponse.ok(response));
    }
}
