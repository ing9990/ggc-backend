package com.gigacoffeebackend.products.product.domain;

import com.gigacoffeebackend.category.domain.Category;
import com.gigacoffeebackend.global.dto.BaseEntity;
import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.products.option.domain.ProductOption;
import com.gigacoffeebackend.store.domain.Store;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.*;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "product", uniqueConstraints = @UniqueConstraint(columnNames = {"store_id", "product_name"}))
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Embedded
    private ProductName name;

    @Embedded
    private ProductPrice price;

    @ManyToOne
    @JoinColumn(name = "category_name")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;

    /**
     * 연관관계가 풀릴 경우 ProductOption 엔티티가 삭제되도록 구성
     */
    @OneToMany(orphanRemoval = true)
    private Set<ProductOption> options = new HashSet<>();

    public static Product makeProductWith(final Store store, final ProductName name, final ProductPrice price) {
        return new Product(store, name, price, null);
    }

    public void changeCategory(final Category category) {
        if (this.category == category) {
            throw new BusinessException(CATEGORY_IS_NOT_CHANGED);
        }
        this.category = category;
    }

    public void addOptions(Collection<ProductOption> options) {
        this.options.addAll(new HashSet<>(options));
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
