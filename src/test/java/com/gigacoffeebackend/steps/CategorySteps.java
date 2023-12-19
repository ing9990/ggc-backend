package com.gigacoffeebackend.steps;

import com.gigacoffeebackend.category.ui.request.AddCategoryRequest;

import java.util.Set;

public class CategorySteps {

    public static AddCategoryRequest 카테고리생성_요청(String name, String displayName, Set<Long> productIds) {
        return new AddCategoryRequest(name, displayName, productIds);
    }

}
