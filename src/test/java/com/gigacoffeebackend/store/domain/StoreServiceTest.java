package com.gigacoffeebackend.store.domain;

import static com.gigacoffeebackend.global.exceptions.ErrorCode.STORE_DUPLICATED;
import static com.gigacoffeebackend.global.exceptions.ErrorCode.STORE_DUPLICATED_ON_UPDATE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.gigacoffeebackend.global.exceptions.BusinessException;
import com.gigacoffeebackend.global.exceptions.ErrorCode;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class StoreServiceTest {

    @Autowired
    private StoreService storeService;

    @Autowired
    private StoreRepository storeRepository;

    @DisplayName("스토어 이름과 위치 이름은 동시에 중복될 수 없다.")
    @Test
    void throws_exception_if_store_infos_is_duplicate() {
        // given
        스토어_저장("만유인력", "합정역점");
        StoreName storeName = new StoreName("만유인력");
        LocationName locationName = new LocationName("합정역점");

        // when
        assertThatThrownBy(() -> storeService.saveStore(storeName, locationName))
            .isInstanceOf(BusinessException.class)
            .hasMessage(STORE_DUPLICATED.getMessage());
    }

    @DisplayName("스토어를 저장한다.")
    @Test
    void save_store_with_name_and_location_name() {
        // given
        String name = "만유인력";
        String locationName = "합정역점";

        // when
        Store store = storeService.saveStore(new StoreName(name), new LocationName(locationName));

        // then
        assertThat(storeRepository.count()).isNotZero();
        Optional<Store> findById = storeRepository.findById(store.getId());
        assertThat(findById).isPresent();
        Store find = findById.get();
        assertThat(find).isNotNull();
        assertThat(find.getName())
            .isEqualTo(new StoreName("만유인력"));
        assertThat(find.getLocationName())
            .isEqualTo(new LocationName("합정역점"));
    }

    @DisplayName("스토어의 정보를 수정할 수 있다.")
    @Test
    void update_store_infos() {
        // given
        //final Store foundStore, final StoreName storeName,
        //        final LocationName locationName
        Store store = 스토어_저장("만유인력", "합정역점");
        StoreName storeName = new StoreName("만유인력-리뉴얼");
        LocationName locationName = new LocationName("합정점");

        // when
        storeService.updateStore(store, storeName, locationName);

        // then
        Optional<Store> findById = storeRepository.findById(store.getId());
        assertThat(findById).isPresent();
        Store find = findById.get();
        assertThat(find.getLocationName()).isEqualTo(new LocationName("합정점"));
        assertThat(find.getName()).isEqualTo(new StoreName("만유인력-리뉴얼"));
        assertThat(find.getFullName()).isEqualTo("만유인력-리뉴얼 합정점");
    }

    @DisplayName("수정한 스토어의 정보가 이미 존재하는 스토어의 정보와 같다면 스토어 정보를 변경할 수 없다.")
    @Test
    void throws_exception_stores_info_was_duplicated_when_update_store_infos() {
        // given
        //final Store foundStore, final StoreName storeName,
        //        final LocationName locationName
        Store store = 스토어_저장("만유인력", "합정역점");
        Store store2 = 스토어_저장("만유인력-리뉴얼", "합정점");

        StoreName storeName = new StoreName("만유인력-리뉴얼");
        LocationName locationName = new LocationName("합정점");

        // when
        assertThatThrownBy(() -> storeService.updateStore(store, storeName, locationName))
            .isInstanceOf(BusinessException.class)
            .hasMessage(STORE_DUPLICATED_ON_UPDATE.getMessage());
    }


    private Store 스토어_저장(String name, String locationName) {
        Store firstStore = Store.makeStore(new StoreName(name), new LocationName(locationName));
        return storeRepository.save(firstStore);
    }
}