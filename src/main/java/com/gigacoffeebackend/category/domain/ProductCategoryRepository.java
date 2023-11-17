package com.gigacoffeebackend.category.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductCategoryRepository extends JpaRepository<Category, String> {

    @Query("select p from Category p where p.name = ?1")
    Optional<Category> findProductCategoryByName(String name);

    @Query("select p from Category p where p.name = ?1")
    Category getProductCategoryByName(String name);
}
