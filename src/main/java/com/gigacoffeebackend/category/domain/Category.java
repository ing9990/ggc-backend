package com.gigacoffeebackend.category.domain;

import com.gigacoffeebackend.global.dto.BaseEntity;
import com.gigacoffeebackend.products.product.domain.Product;
import com.gigacoffeebackend.store.domain.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.CascadeType.ALL;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category extends BaseEntity {

    @Id
    private String name;

    @Column(nullable = false)
    private String displayName;

    @ManyToOne
    @JoinColumn(name = "store_category_name")
    private Store store;

    @OneToMany(cascade = ALL, orphanRemoval = true, mappedBy = "category")
    private Set<Product> products = new HashSet<>();

    protected Category(Store store, String name, String displayName, Set<Product> products) {
        this.store = store;
        this.name = name;
        this.displayName = displayName;
        this.products = products;
    }

    public static Category makeCategory(Store store, String name, String displayName,
        Set<Product> products) {
        return new Category(store, name, displayName, products);
    }

    public static Category makeDefault(Store store) {
        return new Category(store, Default.ALL.getName(), Default.ALL.getDisplayName(), Set.of());
    }


    public Category addProducts(Set<Product> products) {
        this.products.addAll(products);
        return this;
    }
}
