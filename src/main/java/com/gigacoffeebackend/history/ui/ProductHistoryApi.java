package com.gigacoffeebackend.history.ui;

import com.gigacoffeebackend.global.dto.ApiResponse;
import com.gigacoffeebackend.history.domain.ProductHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/stores/{storeId}/histories")
@RequiredArgsConstructor
public class ProductHistoryApi {

    private final ProductHistoryService productHistoryService;

    @GetMapping
    ResponseEntity<ApiResponse<StoreHistoriesResponse>> storeHistories(@PathVariable Long storeId) {
        final StoreHistoriesResponse response = productHistoryService.findHistoriesByStoreId(storeId);

        return ResponseEntity.ok(ApiResponse.ok(response));
    }
}
