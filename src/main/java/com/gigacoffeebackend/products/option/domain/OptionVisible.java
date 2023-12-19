package com.gigacoffeebackend.products.option.domain;

import static com.gigacoffeebackend.products.product.domain.ProductOptionVisible.VISIBLE;

import com.gigacoffeebackend.products.product.domain.ProductOptionVisible;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class OptionVisible {

    @Column(name = "option_visible", nullable = false)
    private final ProductOptionVisible visible;

    public OptionVisible(ProductOptionVisible visible) {
        this.visible = visible;
    }

    protected OptionVisible() {
        visible = VISIBLE;
    }

    public static OptionVisible visible() {
        return new OptionVisible(VISIBLE);
    }

}
