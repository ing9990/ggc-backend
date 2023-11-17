package com.gigacoffeebackend.store.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("select s from Store s where s.name = ?1 and s.locationName = ?2")
    Store getStoreByNameAndLocationName(String name, String locationName);

    @Query("select s from Store s where s.name = ?1 and s.locationName = ?2")
    Optional<Store> findStoreByNameAndLocationName(String name, String locationName);

    @Query("select s from Store s where concat(s.name, s.locationName) = ?1 ")
    Optional<Store> findStoreByFullName(String fullName);

    @Query("select count(s) > 0 from Store s where s.name = ?1 and s.locationName = ?2")
    boolean existsStoreByNameAndLocationName(String name, String locationName);
}
