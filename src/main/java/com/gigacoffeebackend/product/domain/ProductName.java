package com.gigacoffeebackend.product.domain;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import java.util.regex.Pattern;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import java.util.Objects;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.PRODUCT_NAME_IS_EMPTY;
import static com.gigacoffeebackend.global.exceptions.ErrorCode.PRODUCT_NAME_IS_INVALID;

@Embeddable
public class ProductName {

    private static final Pattern NAME_PATTERN = Pattern.compile("^[a-zA-Z가-힣0-9]{2,}$");

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

        if (!NAME_PATTERN.matcher(name).matches()) {
            throw new InvalidNamingException("빈칸 또는 특수문자는 사용 불가합니다.", name,
                PRODUCT_NAME_IS_INVALID);
        }

    }

    public String toString() {
        return name;
    }

    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ProductName productName = (ProductName) o;
        return name.equals(productName.name);
    }

    public int hashCode() {
        return Objects.hash(name);
    }
}