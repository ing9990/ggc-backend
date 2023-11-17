package com.gigacoffeebackend.category.domain;

import com.gigacoffeebackend.global.dto.BaseEntity;
import com.gigacoffeebackend.product.domain.Product;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Category extends BaseEntity {

    @Id
    private String name;

    @Column(nullable = false)
    private String displayName;

    @OneToMany
    private Set<Product> products = new HashSet<>();

    public Category(String name, String displayName) {
        this.name = name;
        this.displayName = displayName;
        this.products = new HashSet<>();
    }
}
