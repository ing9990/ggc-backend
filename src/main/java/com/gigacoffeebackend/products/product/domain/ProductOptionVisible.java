package com.gigacoffeebackend.products.product.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductOptionVisible {
    HIDDEN("숨김"), VISIBLE("보임");

    private final String text;
}
