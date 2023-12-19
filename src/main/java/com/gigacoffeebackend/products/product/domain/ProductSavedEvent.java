package com.gigacoffeebackend.products.product.domain;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ProductSavedEvent extends ApplicationEvent {

    public ProductSavedEvent(Object source) {
        super(source);
    }
}
