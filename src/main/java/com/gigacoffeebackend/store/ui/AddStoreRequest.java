package com.gigacoffeebackend.store.ui;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddStoreRequest {

    // 할리스 커피
    @NotBlank(message = "매장 명이 빈 값입니다.")
    private String name;

    // 합정역 점
    @NotBlank(message = "매장 위치가 빈 값입니다.")
    private String location;

}
