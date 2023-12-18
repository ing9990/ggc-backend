package com.gigacoffeebackend.history.domain;

import static com.gigacoffeebackend.product.domain.ProductEventType.CREATED;

import com.gigacoffeebackend.history.dto.ProductHistoryDto;
import com.gigacoffeebackend.history.ui.ProductHistoryResponse;
import com.gigacoffeebackend.history.ui.StoreHistoriesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductHistoryService {

    private final ProductHistoryRepository productHistoryRepository;

    public void saveProductHistory(final ProductHistoryDto productHistoryDto) {
        ProductHistoryCollection collection = ProductHistoryCollection.of(
            productHistoryDto.getStoreId(),
            productHistoryDto.getStoreName(),
            productHistoryDto.getLocationName(),
            productHistoryDto.getProductName(),
            productHistoryDto.getProductPrice(),
            CREATED
        );
        productHistoryRepository.save(collection);
    }

    public StoreHistoriesResponse findHistoriesByStoreId(Long storeId) {
        List<ProductHistoryCollection> histories = productHistoryRepository.findProductHistoriesByStoreId(
            storeId);
        List<ProductHistoryResponse> response = toHistorieResponse(histories);
        return StoreHistoriesResponse.from(response);
    }

    private List<ProductHistoryResponse> toHistorieResponse(
        List<ProductHistoryCollection> histories) {
        return histories.stream().map(ProductHistoryResponse::from)
            .collect(Collectors.toList());
    }
}
