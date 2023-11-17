package com.gigacoffeebackend.store.domain;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.STORE_DUPLICATED;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StoreService {

    private final StoreRepository storeRepository;

    @Transactional
    public Store saveStore(final String name, final String locationName) {
        checkStoreDuplicate(name, locationName);
        return storeRepository.save(Store.makeStore(name, locationName));
    }

    public Store getStoreByFullName(final String name, final String locationName) {
        return findStoreByFullName(name, locationName)
                .orElseThrow(StoreNotFoundException::new);
    }

    public Optional<Store> findStoreByFullName(final String name, final String locationName) {
        return storeRepository.findStoreByNameAndLocationName(name, locationName);
    }

    public Optional<Store> findStoreByFullName(final String fullName) {
        return storeRepository.findStoreByFullName(fullName);
    }

    public Store getStoreById(final Long storeId) {
        return storeRepository.findById(storeId)
                .orElseThrow(StoreNotFoundException::new);
    }

    public Optional<Store> findStoreById(Long storeId) {
        return storeRepository.findById(storeId);
    }

    private void checkStoreDuplicate(String name, String locationName) {
        if (storeRepository.existsStoreByNameAndLocationName(name, locationName)) {
            throw new BusinessException(STORE_DUPLICATED);
        }
    }
}
