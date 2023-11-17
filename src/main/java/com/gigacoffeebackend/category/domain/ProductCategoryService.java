package com.gigacoffeebackend.category.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductCategoryService {

    private final ProductCategoryRepository productCategoryRepository;

    public Category get(String name) {
        return productCategoryRepository.findProductCategoryByName(name)
                .orElseThrow(CategoryNotFoundException::new);
    }

    public Optional<Category> find(String name) {
        return productCategoryRepository.findProductCategoryByName(name);
    }
}
