package com.gigacoffeebackend.category.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum Default {
    ALL("all", "전체");

    private final String name;
    private final String displayName;
}
