package com.gigacoffeebackend.products.option.domain;

import com.gigacoffeebackend.products.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    @Transactional
    @Modifying
    @Query("delete from ProductOption p where p.product = ?1")
    void deleteAllByProduct(Product product);
}
