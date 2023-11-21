package com.gigacoffeebackend.category.ui;

import com.gigacoffeebackend.category.dto.CategoryNames;
import com.gigacoffeebackend.category.service.CategoryIntegration;
import com.gigacoffeebackend.global.dto.ApiResponse;
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
        CategoryResponse response = categoryIntegration.addCategory(storeId, request);

        return ok(ApiResponse.ok(response));
    }

    @GetMapping
    ResponseEntity<ApiResponse<CategoryNames>> categories(@PathVariable Long storeId) {
        CategoryNames response = categoryIntegration.findCategories(storeId);

        return ok(ApiResponse.ok(response));
    }

    @GetMapping("/{categoryName}/products")
    ResponseEntity<ApiResponse<CategoryProductResponse>> categorieProducts(@PathVariable Long storeId, @PathVariable String categoryName) {
        CategoryProductResponse response = categoryIntegration.findProducts(storeId, categoryName);

        return ok(ApiResponse.ok(response));
    }
}