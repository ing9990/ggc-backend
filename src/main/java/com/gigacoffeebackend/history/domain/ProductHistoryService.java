package com.gigacoffeebackend.history.domain;

import com.gigacoffeebackend.history.dto.SaveProductHistoryDto;
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

    public void saveProductHistory(final SaveProductHistoryDto saveProductHistoryDto) {
        ProductHistoryCollection collection = ProductHistoryCollection.of(
                saveProductHistoryDto.getStoreId(),
                saveProductHistoryDto.getStoreName(),
                saveProductHistoryDto.getLocationName(),
                saveProductHistoryDto.getProductName(),
                saveProductHistoryDto.getProductPrice());
        
        productHistoryRepository.save(collection);
    }

    public StoreHistoriesResponse findHistoriesByStoreId(Long storeId) {
        List<ProductHistoryCollection> histories = productHistoryRepository.findProductHistoriesByStoreId(storeId);
        List<ProductHistoryResponse> response = toHistorieResponse(histories);
        return StoreHistoriesResponse.from(response);
    }

    private List<ProductHistoryResponse> toHistorieResponse(List<ProductHistoryCollection> histories) {
        return histories.stream().map(ProductHistoryResponse::from)
                .collect(Collectors.toList());
    }
}
