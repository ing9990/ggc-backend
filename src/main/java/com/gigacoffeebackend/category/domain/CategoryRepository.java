package com.gigacoffeebackend.category.domain;

import com.gigacoffeebackend.store.domain.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, String> {

    @Query("select c from Category c where c.store = ?1 and c.name = ?2")
    Optional<Category> findCategoryByStoreAndName(Store store, String name);
}
