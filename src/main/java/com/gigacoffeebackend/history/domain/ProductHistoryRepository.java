package com.gigacoffeebackend.history.domain;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductHistoryRepository extends MongoRepository<ProductHistoryCollection, String> {

    List<ProductHistoryCollection> findProductHistoriesByStoreId(Long storeId);
}
