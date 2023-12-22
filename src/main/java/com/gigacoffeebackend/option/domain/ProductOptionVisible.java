package com.gigacoffeebackend.option.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductOptionVisible {
    HIDDEN("숨김"), VISIBLE("보임");

    private final String text;
}
