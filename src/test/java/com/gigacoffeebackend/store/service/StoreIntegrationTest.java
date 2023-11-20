package com.gigacoffeebackend.store.service;

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
        String name = "메가커피";
        String locationName = "합정역점";
        AddStoreRequest addStoreRequest = new AddStoreRequest(name, locationName);

        // when
        StoreResponse storeResponse = storeIntegration.addStore(addStoreRequest);

        // then
        Assertions.assertThat(storeResponse.getStoreFullName()).isEqualTo("메가커피 합정역점");
    }


}