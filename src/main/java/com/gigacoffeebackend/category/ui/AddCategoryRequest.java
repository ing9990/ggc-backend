package com.gigacoffeebackend.category.ui;

import com.gigacoffeebackend.global.aop.annotation.AlphabetOnly;
import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.*;

@Getter
public class AddCategoryRequest {

    @NotBlank(message = "카테고리 이름을 입력하세요.")
    @AlphabetOnly
    private String name;

    @NotBlank(message = "카테고리의 표시 명을 입력하세요.")
    private String displayName;

    private Set<Long> products = new HashSet<>();

    public AddCategoryRequest(
        @Valid @AlphabetOnly @NotBlank(message = "카테고리 이름을 입력하세요.") String name,
        @Valid @NotBlank(message = "카테고리의 표시 명을 입력하세요.") String displayName,
        Set<Long> products
    ) {
        this.name = name;
        this.displayName = displayName;
        this.products = Objects.isNull(products) ? Set.of() : products;
    }

    protected AddCategoryRequest() {

    }
}
