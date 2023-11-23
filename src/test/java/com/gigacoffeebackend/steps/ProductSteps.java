package com.gigacoffeebackend.steps;

import com.gigacoffeebackend.product.ui.AddProductRequest;

import java.util.Set;

public class ProductSteps {

    public static AddProductRequest 상품등록요청_생성(String productName, int productPrice, String categoryName) {
        return new AddProductRequest(productName, productPrice, categoryName);
    }
}
