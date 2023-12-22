package com.gigacoffeebackend.option.domain;

import static java.util.Set.of;

import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.product.domain.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductOptionService {

    private final ProductOptionRepository productOptionRepository;
    private final ProductRepository productRepository;

    @Transactional
    public Product addOption(final Product product, final OptionName name) {
        ProductOption savedOption = productOptionRepository.save(ProductOption.makeOption(product, name));
        product.addOptions(of(savedOption));
        return product;
    }

    @Transactional
    public Product addOption(final Product product, final OptionName name, final OptionPrice price) {
        ProductOption savedOption = productOptionRepository.save(ProductOption.makeOption(product, name, price));
        product.addOptions(of(savedOption));
        return product;
    }

    @Transactional
    public Product clearAllOptions(final Product product) {
//        productOptionRepository.deleteAllByProduct(product);
        product.getOptions().clear();
        return product;
    }
}