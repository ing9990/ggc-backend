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

import static javax.persistence.CascadeType.ALL;

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

    @Embedded
    private StoreName name;

    @Embedded
    private LocationName locationName;

    @OneToMany(mappedBy = "store", cascade = ALL, orphanRemoval = true)
    private Set<Product> products = new HashSet<>();

    @OneToMany(cascade = ALL, orphanRemoval = true)
    private Set<Category> categories = new HashSet<>();

    @Builder
    private Store(final StoreName name, final LocationName locationName) {
        this.name = name;
        this.locationName = locationName;
    }

    public static Store makeStore(StoreName name, LocationName locationName) {
        return Store.builder()
            .name(name)
            .locationName(locationName)
            .build();
    }

    public String getFullName() {
        return this.name + " " + this.locationName;
    }

    public void addProduct(final Product product) {
        this.products.add(product);
    }

    public void addCategory(final Category category) {
        this.categories.add(category);
    }

    public void updateNameAndLocationName(StoreName name, LocationName location) {
        this.name = name;
        this.locationName = location;
    }

    public Store deleteCategory(final Category category) {
        categories.remove(category);
        return this;
    }

    public Store deleteProduct(final Product product) {
        products.remove(product);
        return this;
    }
}
