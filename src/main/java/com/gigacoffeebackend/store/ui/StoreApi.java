package com.gigacoffeebackend.store.ui;

import com.gigacoffeebackend.global.dto.ApiResponse;
import com.gigacoffeebackend.store.service.StoreIntegration;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        StoreResponse storeResponse = storeIntegration.addStore(addStoreRequest);
        return status(OK).body(ok(storeResponse));
    }
}
