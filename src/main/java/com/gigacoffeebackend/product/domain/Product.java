package com.gigacoffeebackend.product.domain;

import com.gigacoffeebackend.category.domain.Category;
import com.gigacoffeebackend.global.dto.BaseEntity;
import com.gigacoffeebackend.option.domain.ProductOption;
import com.gigacoffeebackend.store.domain.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "product",
        uniqueConstraints = @UniqueConstraint(columnNames = {"store_id", "product_name"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    // 아메리카노, 라떼
    @Column(name = "product_name", nullable = false)
    private String name;

    // 4500
    @Column(name = "product_price", nullable = false)
    private int price;

    @OneToMany
    private Set<Category> categories = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    @OneToMany(mappedBy = "optionSelects")
    Set<ProductOption> productOptions = new HashSet<>();

    private Product(Store store, String name, int price) {
        this.store = store;
        this.name = name;
        this.price = price;
    }

    public static Product makeProductWith(Store store, String name, int price) {
        return new Product(store, name, price);
    }
}
