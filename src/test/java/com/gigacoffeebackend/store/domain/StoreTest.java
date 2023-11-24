package com.gigacoffeebackend.store.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class StoreTest {

    @DisplayName("새로운 스토어를 생성한다")
    @Test
    void create_store() {
        // given
        String name = "메가커피";
        String locationName = "합정역점";

        // when
        Store store = Store.makeStore(new StoreName(name), new LocationName(locationName));

        // then
        assertThat(store.getName()).isEqualTo(new StoreName("메가커피"));
        assertThat(store.getLocationName()).isEqualTo(new LocationName("합정역점"));
    }

    @DisplayName("스토어의 풀네임은 네임과 지역 명을 더한 값이다.")
    @Test
    void getFullName() {
        // given
        String name = "메가커피";
        String locationName = "합정역점";

        // when
        Store store = Store.makeStore(new StoreName(name), new LocationName(locationName));

        // then
        Assertions.assertThat(store.getFullName()).isEqualTo("메가커피 합정역점");
    }
}