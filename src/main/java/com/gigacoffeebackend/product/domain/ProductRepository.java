package com.gigacoffeebackend.product.domain;

import com.gigacoffeebackend.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("select (count(p) > 0) from Product p where p.store = ?1 and p.name = ?2")
    boolean existsByStoreAndName(Store store, ProductName productName);
}
