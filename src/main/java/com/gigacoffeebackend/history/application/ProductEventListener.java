package com.gigacoffeebackend.history.application;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacoffeebackend.history.domain.ProductHistoryService;
import com.gigacoffeebackend.history.dto.ProductHistoryDto;
import com.gigacoffeebackend.product.domain.ProductEventSource;
import com.gigacoffeebackend.product.domain.ProductSavedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProductEventListener {

    private final ObjectMapper om;
    private final ProductHistoryService productHistoryService;

    @EventListener(ProductSavedEvent.class)
    public void handleProductSaved(final ProductSavedEvent productSavedEvent) {
        ProductEventSource source = (ProductEventSource) productSavedEvent.getSource();
        productHistoryService.saveProductHistory(ProductHistoryDto.from(source));
    }
}
