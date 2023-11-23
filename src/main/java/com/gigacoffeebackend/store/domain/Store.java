package com.gigacoffeebackend.store.domain;

import com.gigacoffeebackend.category.domain.Category;
import com.gigacoffeebackend.global.dto.BaseEntity;
import com.gigacoffeebackend.product.domain.Product;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "store",
        uniqueConstraints = @UniqueConstraint(columnNames = {"name", "location_name"})
)
@Entity
@Getter
public class Store extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 메가커피, 스타벅스, 커피빈 ...
    @Column(name = "name", nullable = false)
    private String name;

    // 합정역점, 서면역점, 합정중앙점, 부평점...
    @Column(name = "location_name", nullable = false)
    private String locationName;

    // 스토어에서 판매 중인 상품들 - 자세한건 Product 도메인에서 관리
    @OneToMany(mappedBy = "store")
    private Set<Product> products = new HashSet<>();

    // 스토어의 관리자가 추가한 카테고리.
    @OneToMany
    private Set<Category> storeCategory = new HashSet<>();

    @Builder
    private Store(final String name, final String locationName) {
        this.name = name;
        this.locationName = locationName;
    }

    protected static Store makeStore(String name, String locationName) {
        return Store.builder()
                .name(name)
                .locationName(locationName)
                .build();
    }

    protected Store withDefault() {
        storeCategory.add(Category.makeDefault(this));
        return this;
    }

    public String getFullName() {
        return this.name + " " + this.locationName;
    }

    public void addProduct(final Product product) {
        this.products.add(product);
    }

    public void addCategory(final Category category) {
        this.storeCategory.add(category);
    }
}
