package com.gigacoffeebackend.store.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacoffeebackend.ApiTest;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.service.StoreIntegration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreApi.class)
@ActiveProfiles("test")
class StoreApiExceptionCaseTest extends ApiTest {

    @MockBean
    private StoreIntegration storeIntegration;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("스토어 이름과 지역명이 모두 같을 수 없다.")
    @Test
    void make_duplicated_store() throws Exception {
        final String name = "AA커피";
        final String locationName = "홍대입구역점";

        Store store = 스토어가_추가되었다(name, locationName);
        final StoreResponse response = StoreResponse.from(store);

        when(storeIntegration.addStore(any(AddStoreRequest.class)))
                .thenReturn(response);

        AddStoreRequest request = new AddStoreRequest(name, locationName);

        mockMvc.perform(post("/api/v1/stores")
                        .header(CONTENT_TYPE, APPLICATION_JSON)
                        .content(om.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value("S03"))
                .andExpect(jsonPath("$.message").value("스토어 이름이 중복되었습니다."));
    }


    Store 스토어가_추가되었다(String name, String locationName) {
        return Store.makeStore(name, locationName);
    }

}
