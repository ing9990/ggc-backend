package com.gigacoffeebackend.product.domain;

import com.gigacoffeebackend.category.domain.Category;
import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.store.domain.Store;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionContext;
import org.springframework.transaction.reactive.TransactionContextManager;
import org.springframework.transaction.reactive.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.PRODUCT_DUPLICATE;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public Product saveProduct(Store store, final ProductName productName, final ProductPrice productPrice) {
        validateStoreProductDuplicate(store, productName);
        return productRepository.save(Product.makeProductWith(store, productName, productPrice));
    }

    @Transactional
    public void addCategoryToProduct(Product product, Category category) {
        product.changeCategory(category);
    }

    public Set<Product> findAllByIds(Set<Long> products) {
        return new HashSet<>(productRepository.findAllById(products));
    }

    private void validateStoreProductDuplicate(Store store, ProductName productName) {
        if (productRepository.existsByStoreAndName(store, productName)) {
            throw new BusinessException(PRODUCT_DUPLICATE);
        }
    }

    public Optional<Product> findProductByStoreAndProductId(Store store, Long productId) {
        return productRepository.findProductByStoreAndId(store, productId);
    }
}