package com.gigacoffeebackend.store.ui;

import com.gigacoffeebackend.global.dto.ApiResponse;
import com.gigacoffeebackend.store.application.StoreIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static com.gigacoffeebackend.global.dto.ApiResponse.ok;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/v1/stores")
@RequiredArgsConstructor
public class StoreApi {

    private final StoreIntegration storeIntegration;

    @PostMapping
    ResponseEntity<ApiResponse<StoreResponse>> addStore(
            @Valid @RequestBody AddStoreRequest addStoreRequest
    ) {
        StoreResponse response = storeIntegration.addStore(addStoreRequest);
        return status(OK).body(ok(response));
    }

    @GetMapping("/{storeId}")
    ResponseEntity<ApiResponse<TotalStoreResponse>> store(
            @PathVariable Long storeId
    ) {
        TotalStoreResponse response = storeIntegration.findStore(storeId);

        return status(OK).body(ok(response));
    }

    @PutMapping("/{storeId}")
    ResponseEntity<ApiResponse<TotalStoreResponse>> update(
            @PathVariable Long storeId,
            @Valid @RequestBody UpdateStoreRequest updateStoreRequest
    ) {
        TotalStoreResponse response = storeIntegration.updateStore(storeId, updateStoreRequest);

        return status(OK).body(ok(response));
    }
}
