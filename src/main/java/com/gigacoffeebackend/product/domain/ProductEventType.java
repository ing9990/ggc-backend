package com.gigacoffeebackend.product.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductEventType {
    CREATED("created"),
    UPDATED("updated"),
    DELETED("deleted");

    private final String storedType;
}
