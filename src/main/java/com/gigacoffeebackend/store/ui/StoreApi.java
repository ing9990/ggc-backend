package com.gigacoffeebackend.store.ui;

import com.gigacoffeebackend.global.dto.ApiResponse;
import com.gigacoffeebackend.store.application.StoreIntegration;
import com.gigacoffeebackend.store.ui.request.AddStoreRequest;
import com.gigacoffeebackend.store.ui.request.UpdateStoreRequest;
import com.gigacoffeebackend.store.ui.response.StoreResponse;
import com.gigacoffeebackend.store.ui.response.TotalStoreResponse;
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
        final StoreResponse response = storeIntegration.addStore(addStoreRequest);
        return status(OK).body(ok(response));
    }

    @GetMapping("/{storeId}")
    ResponseEntity<ApiResponse<TotalStoreResponse>> store(
            @PathVariable Long storeId
    ) {
        final TotalStoreResponse response = storeIntegration.findStore(storeId);

        return status(OK).body(ok(response));
    }

    @PutMapping("/{storeId}")
    ResponseEntity<ApiResponse<TotalStoreResponse>> update(
            @PathVariable Long storeId,
            @Valid @RequestBody UpdateStoreRequest updateStoreRequest
    ) {
        final TotalStoreResponse response = storeIntegration.updateStore(storeId, updateStoreRequest);

        return status(OK).body(ok(response));
    }
}
