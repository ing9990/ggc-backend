package com.gigacoffeebackend.store.ui;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AddStoreRequest {

    @NotBlank(message = "매장 명이 빈 값입니다.")
    private String name;

    @NotBlank(message = "매장 위치가 빈 값입니다.")
    private String location;

}
