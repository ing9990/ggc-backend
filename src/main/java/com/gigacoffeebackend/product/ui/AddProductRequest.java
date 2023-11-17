package com.gigacoffeebackend.product.ui;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddProductRequest {

    @NotEmpty(message = "상품 명이 빈 값입니다.")
    private String productName;

    @Positive(message = "상품 가격은 음수일 수 없습니다.")
    @NotNull
    private int productPrice;

    private String categoryName;
}
