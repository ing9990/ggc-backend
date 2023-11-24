package com.gigacoffeebackend.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StoreRepository extends JpaRepository<Store, Long> {
    @Query("select count(s) > 0 from Store s where s.name = ?1 and s.locationName = ?2")
    boolean existsStoreByNameAndLocationName(StoreName name, LocationName locationName);
}
