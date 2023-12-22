package com.gigacoffeebackend.product.ui.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class AddProductRequest {

    @NotEmpty(message = "상품 명이 빈 값입니다.")
    private String productName;

    @Positive(message = "상품 가격은 음수일 수 없습니다.")
    @NotNull
    private int productPrice;

    private String categoryName;

}
