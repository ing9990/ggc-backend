package com.gigacoffeebackend.option.domain;

import static com.gigacoffeebackend.option.domain.ProductOptionVisible.VISIBLE;

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
