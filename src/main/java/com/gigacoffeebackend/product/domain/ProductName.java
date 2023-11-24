package com.gigacoffeebackend.product.domain;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.util.Objects;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.PRODUCT_NAME_IS_EMPTY;

@Embeddable
public class ProductName {

    @Column(name = "product_name", nullable = false)
    private String name;

    protected ProductName() {

    }

    public ProductName(String name) {
        this.name = name;
    }

    public void isValid() {
        if (!StringUtils.hasText(name)) {
            throw new BusinessException(PRODUCT_NAME_IS_EMPTY);
        }
    }

    public String toString() {
        return name;
    }

    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final ProductName productName = (ProductName) o;
        return name.equals(productName.name);
    }

    public int hashCode() {
        return Objects.hash(name);
    }
}