package com.gigacoffeebackend.history.application;

import com.gigacoffeebackend.history.domain.ProductHistoryService;
import com.gigacoffeebackend.history.dto.SaveProductHistoryDto;
import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.product.domain.ProductSavedEvent;
import com.gigacoffeebackend.store.domain.Store;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class ProductEventListener {

    private final ProductHistoryService productHistoryService;

    @EventListener(ProductSavedEvent.class)
    public void handleProductSaved(ProductSavedEvent productSavedEvent) {
        Product product = (Product) productSavedEvent.getSource();
        Store store = product.getStore();

        productHistoryService.saveProductHistory(SaveProductHistoryDto.from(store, product));
    }
}
