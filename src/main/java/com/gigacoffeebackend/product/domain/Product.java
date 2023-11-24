package com.gigacoffeebackend.product.domain;

import com.gigacoffeebackend.category.domain.Category;
import com.gigacoffeebackend.global.dto.BaseEntity;
import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.store.domain.Store;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.*;
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
    @Embedded
    private ProductName name;

    // 4500
    @Embedded
    private ProductPrice price;

    @ManyToOne
    private Category category;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    public static Product makeProductWith(final Store store, final ProductName name, final ProductPrice price, final Category category) {
        return new Product(store, name, price, category);
    }

    public static Product makeProductWith(final Store store, final ProductName name, final ProductPrice price) {
        return new Product(store, name, price, null);
    }

    public void changeCategory(final Category category) {
        if (this.category == category) {
            throw new BusinessException(CATEGORY_IS_NOT_CHANGED);
        }
        this.category = category;
    }

    private Product(Store store, ProductName name, ProductPrice price, final Category category) {
        checkValidate(name, price);

        this.store = store;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    private void checkValidate(ProductName name, ProductPrice price) {
        name.isValid();
        price.throwIsNotPositive();
        price.throwIsNotDivisibleBy100();
    }
}
