package com.gigacoffeebackend.store.ui;

import com.gigacoffeebackend.store.domain.LocationName;
import com.gigacoffeebackend.store.domain.StoreName;
import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
public class AddStoreRequest {

    // 할리스 커피
    @NotBlank(message = "매장 명이 빈 값입니다.")
    private String name;

    // 합정역 점
    @NotBlank(message = "매장 위치가 빈 값입니다.")
    private String location;

    public StoreName getName() {
        return new StoreName(name);
    }

    public LocationName getLocation() {
        return new LocationName(location);
    }
}
