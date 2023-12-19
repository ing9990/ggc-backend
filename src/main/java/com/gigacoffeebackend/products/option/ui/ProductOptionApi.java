package com.gigacoffeebackend.products.option.ui;

import static com.gigacoffeebackend.global.dto.ApiResponse.ok;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.ResponseEntity.status;

import com.gigacoffeebackend.global.dto.ApiResponse;
import com.gigacoffeebackend.products.option.application.ProductOptionIntegration;
import com.gigacoffeebackend.products.option.ui.request.AddOptionRequest;
import com.gigacoffeebackend.products.option.ui.response.ProductAndOptionsResponse;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products/{productId}/options")
@RequiredArgsConstructor
@Slf4j
public class ProductOptionApi {

    private final ProductOptionIntegration productOptionIntegration;

    @PostMapping
    public ResponseEntity<ApiResponse<ProductAndOptionsResponse>> addOption(
        @PathVariable Long productId,
        @Valid @RequestBody AddOptionRequest addOptionRequest
    ){
        final ProductAndOptionsResponse response = productOptionIntegration.addOption(productId, addOptionRequest);
        return status(CREATED).body(ok(response));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<ProductAndOptionsResponse>> clearOption(
        @PathVariable Long productId
    ){
        final ProductAndOptionsResponse response = productOptionIntegration.clearOptions(productId);

        return status(CREATED).body(ok(response));
    }
}
