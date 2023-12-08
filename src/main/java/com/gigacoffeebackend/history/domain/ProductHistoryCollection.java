package com.gigacoffeebackend.history.domain;

import com.gigacoffeebackend.product.domain.ProductEventType;
import com.gigacoffeebackend.product.domain.ProductName;
import com.gigacoffeebackend.product.domain.ProductPrice;
import com.gigacoffeebackend.store.domain.LocationName;
import com.gigacoffeebackend.store.domain.StoreName;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Document("product_histories")
@Getter
public class ProductHistoryCollection {

    @MongoId
    @Field("history_id")
    private String historyId;

    @Indexed
    @Field("store_id")
    private Long storeId;

    @Field("store_name")
    private String storeName;

    @Field("location_name")
    private String locationName;

    @Field("product_name")
    private String productName;

    @Field("product_price")
    private BigDecimal productPrice;

    @Field("type")
    private String storedType;

    @Field
    @CreatedDate
    private LocalDateTime createdAt;

    @Field
    @LastModifiedDate
    private LocalDateTime updatedAt;

    private ProductHistoryCollection(Long storeId, StoreName storeName, LocationName locationName,
        ProductName productName, ProductPrice productPrice, String type) {
        this.storeId = storeId;
        this.storeName = storeName.toString();
        this.locationName = locationName.toString();
        this.productName = productName.toString();
        this.productPrice = productPrice.getValue();
        this.storedType = type;
    }

    protected ProductHistoryCollection() {
    }

    public static ProductHistoryCollection of(Long storeId, final StoreName storeName,
        final LocationName locationName, final ProductName productName,
        final ProductPrice productPrice, final ProductEventType type) {
        return new ProductHistoryCollection(storeId, storeName, locationName, productName,
            productPrice, type.getStoredType());
    }
}

