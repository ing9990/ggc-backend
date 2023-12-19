package com.gigacoffeebackend.products.option.domain;

import static com.gigacoffeebackend.products.option.domain.OptionPrice.free;
import static com.gigacoffeebackend.products.option.domain.OptionVisible.visible;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

import com.gigacoffeebackend.products.product.domain.Product;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class ProductOption {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(cascade = ALL)
    private Product product;

    @Embedded
    private OptionName name;

    @Embedded
    private OptionPrice price;

    @Embedded
    private OptionVisible visible;

    @Builder
    private ProductOption(final Product product, final OptionName name, final OptionPrice price, final OptionVisible visible) {
        this.product = product;
        this.name = name;
        this.price = price;
        this.visible = visible;
    }

    public static ProductOption makeOption(final Product product, final OptionName name) {
        return ProductOption
            .builder()
            .product(product)
            .name(name)
            .price(free())
            .visible(visible())
            .build();
    }

    public static ProductOption makeOption(final Product product, final OptionName name, final OptionPrice price) {
        return ProductOption.builder()
            .product(product)
            .name(name)
            .price(price)
            .visible(visible())
            .build();
    }
}
