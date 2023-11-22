package com.gigacoffeebackend.category.domain;

import com.gigacoffeebackend.category.domain.Category;
import com.gigacoffeebackend.global.dto.BaseEntity;
import com.gigacoffeebackend.product.domain.Product;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ProductCategory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    @ManyToOne
    private Category category;
}
