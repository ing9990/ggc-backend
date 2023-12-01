package com.gigacoffeebackend.history.ui;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import lombok.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StoreHistoriesResponse {

    List<ProductHistoryResponse> histories = new ArrayList<>();

    public static StoreHistoriesResponse from(List<ProductHistoryResponse> productHistoryResponses) {
        return new StoreHistoriesResponse(productHistoryResponses);
    }
}
