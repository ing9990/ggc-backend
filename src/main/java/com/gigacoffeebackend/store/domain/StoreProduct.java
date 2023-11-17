package com.gigacoffeebackend.store.domain;

import com.gigacoffeebackend.product.domain.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import javax.persistence.OneToMany;

import java.util.HashSet;
import java.util.Set;

import static lombok.AccessLevel.PROTECTED;

@Embeddable
@Getter
@NoArgsConstructor(access = PROTECTED)
public class StoreProduct {

    @OneToMany(mappedBy = "store")
    private Set<Product> products = new HashSet<>();

    public void addProduct(Product product) {
        this.products.add(product);
    }
}
