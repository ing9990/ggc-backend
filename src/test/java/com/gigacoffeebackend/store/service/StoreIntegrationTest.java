package com.gigacoffeebackend.store.service;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.store.ui.AddStoreRequest;
import com.gigacoffeebackend.store.ui.StoreResponse;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StoreIntegrationTest {

    @Autowired
    private StoreIntegration storeIntegration;

    @DisplayName("새로운 스토어를 생성한다.")
    @Test
    void create_store() {
        // given
        AddStoreRequest addStoreRequest = getAddStoreRequest("메가커피", "합정역점");

        // when
        StoreResponse storeResponse = storeIntegration.addStore(addStoreRequest);

        // then
        Assertions.assertThat(storeResponse.getStoreFullName()).isEqualTo("메가커피 합정역점");
    }

    @DisplayName("스토어의 이름과 지역이 모두 같을 수 없다.")
    @Test
    void store_name_and_location_cannot_duplicate() {
        // given
        AddStoreRequest addStoreRequest = getAddStoreRequest("메가커피", "합정역점");
        storeIntegration.addStore(addStoreRequest);

        // when then
        Assertions.assertThatThrownBy(() -> storeIntegration.addStore(addStoreRequest))
                .isInstanceOf(BusinessException.class);
    }


    private AddStoreRequest getAddStoreRequest(String name, String locationName) {
        return new AddStoreRequest(name, locationName);
    }
}