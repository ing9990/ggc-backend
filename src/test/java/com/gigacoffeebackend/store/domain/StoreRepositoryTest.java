package com.gigacoffeebackend.store.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StoreRepositoryTest {

    @Autowired
    private StoreRepository storeRepository;

    @DisplayName("스토어를 등록할 수 있다.")
    @Test
    void save_store() {
        // given
        String name = "메가커피";
        String location = "합정역점";
        Store store = Store.makeStore(new StoreName(name), new LocationName(location));

        // when
        Store savedStore = storeRepository.save(store);

        // then
        assertThat(storeRepository.findAll().size()).isNotZero();
    }

    @DisplayName("등록된 스토어를 아이디를 통해 조회한다.")
    @Test
    void save_and_get_store_with_id() {
        // given
        String name = "메가커피";
        String location = "합정역점";
        Store store = Store.makeStore(new StoreName(name), new LocationName(location));
        Store savedStore = storeRepository.save(store);

        // when
        Optional<Store> foundStore = storeRepository.findById(savedStore.getId());

        // then
        Assertions.assertThat(foundStore).isPresent();
        Assertions.assertThat(foundStore.get().getName()).isEqualTo("메가커피");
    }


    private Store 스토어_생성됨(String name, String location) {
        Store store = Store.makeStore(new StoreName(name), new LocationName(location));
        return storeRepository.save(store);
    }


}