package com.gigacoffeebackend.steps;

import com.gigacoffeebackend.store.ui.request.AddStoreRequest;


public class StoreSteps {
    public static AddStoreRequest 스토어등록요청_생성(String name, String location) {
        return new AddStoreRequest(name, location);
    }
}
