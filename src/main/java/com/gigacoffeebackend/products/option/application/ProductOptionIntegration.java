package com.gigacoffeebackend.products.option.application;

import com.gigacoffeebackend.products.option.domain.ProductOptionService;
import com.gigacoffeebackend.products.option.ui.request.AddOptionRequest;
import com.gigacoffeebackend.products.option.ui.response.ProductAndOptionsResponse;
import com.gigacoffeebackend.products.product.domain.Product;
import com.gigacoffeebackend.products.product.domain.ProductNotFoundException;
import com.gigacoffeebackend.products.product.domain.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ProductOptionIntegration {

    private final ProductService productService;
    private final ProductOptionService productOptionService;

    @Transactional
    public ProductAndOptionsResponse addOption(final Long productId, final AddOptionRequest addOptionRequest) {
        final Product foundProduct = productService.findProductByProductId(productId)
            .orElseThrow(ProductNotFoundException::new);

        Product product = addOptionRequest.mapOptionPrice().map(price -> productOptionService.addOption(foundProduct, addOptionRequest.toOptionName(), addOptionRequest.toOptionPrice()))
            .orElseGet(() -> productOptionService.addOption(foundProduct, addOptionRequest.toOptionName()));

        return ProductAndOptionsResponse.from(product);
    }

    @Transactional
    public ProductAndOptionsResponse clearOptions(final Long productId) {
        final Product foundProduct = productService.findProductByProductId(productId)
            .orElseThrow(ProductNotFoundException::new);

        Product product = productOptionService.clearAllOptions(foundProduct);
        return ProductAndOptionsResponse.from(product);
    }
}