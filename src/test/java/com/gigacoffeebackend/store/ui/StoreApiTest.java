package com.gigacoffeebackend.store.ui;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gigacoffeebackend.ApiTest;
import com.gigacoffeebackend.store.domain.LocationName;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.application.StoreIntegration;
import com.gigacoffeebackend.store.domain.StoreName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StoreApi.class)
@ActiveProfiles("test")
class StoreApiTest extends ApiTest {

    @MockBean
    private StoreIntegration storeIntegration;

    @DisplayName("스토어를 생성한다.")
    @Test
    void create_store() throws Exception {
        final String name = "할리스커피";
        final String locationName = "합정역점";
        final StoreResponse response = StoreResponse.from(Store.makeStore(new StoreName(name), new LocationName(locationName)));
        when(storeIntegration.addStore(any(AddStoreRequest.class)))
            .thenReturn(response);

        AddStoreRequest request = new AddStoreRequest(name, locationName);

        mockMvc
            .perform(post("/api/v1/stores")
                .header(CONTENT_TYPE, APPLICATION_JSON)
                .content(om.writeValueAsString(request)))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.storeFullName").value("할리스커피 합정역점"));
    }
}