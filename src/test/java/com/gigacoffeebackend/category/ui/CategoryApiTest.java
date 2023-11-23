package com.gigacoffeebackend.category.ui;

import com.gigacoffeebackend.ApiTest;
import com.gigacoffeebackend.category.application.CategoryIntegration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Set;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryApi.class)
@ActiveProfiles({"test"})
class CategoryApiTest extends ApiTest {

    @MockBean
    private CategoryIntegration categoryIntegration;


    @DisplayName("스토어에 등록된 카테고리를 가져올 수 있다.")
    @Test
    void get_categories_from_store_id() throws Exception {
        // given
        Long storeId = 1L;

        // when // then
        mockMvc
                .perform(get("/api/v1/stores/" + storeId + "/categories"))
                .andDo(print());
    }


    @DisplayName("카테고리를 생성할 수 있다.")
    @Test
    void create_category() throws Exception {
        // given
        Long storeId = 1L;
        Set<Long> products = Set.of();
        String name = "coffee";
        String displayName = "커피";
        AddCategoryRequest request = new AddCategoryRequest(name, displayName, products);

        // when // then
        mockMvc
                .perform(post("/api/v1/stores/" + storeId + "/categories")
                        .header(AUTHORIZATION, TOKENS.getAccessToken())
                        .contentType(APPLICATION_JSON_VALUE)
                        .content(om.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("OK"))
        ;
    }
}