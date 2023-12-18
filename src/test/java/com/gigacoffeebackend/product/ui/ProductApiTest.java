package com.gigacoffeebackend.product.ui;

import com.gigacoffeebackend.ApiTest;
import com.gigacoffeebackend.product.application.ProductIntegration;
import com.gigacoffeebackend.product.domain.Product;
import com.gigacoffeebackend.product.domain.ProductName;
import com.gigacoffeebackend.product.domain.ProductPrice;
import com.gigacoffeebackend.store.domain.LocationName;
import com.gigacoffeebackend.store.domain.Store;
import com.gigacoffeebackend.store.domain.StoreName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductApi.class)
class ProductApiTest extends ApiTest {

    @MockBean
    private ProductIntegration productIntegration;

    @DisplayName("상품을 추가할 수 있다.")
    @Test
    void add_product() throws Exception {
        // given
        AddProductRequest request = new AddProductRequest("시원한 커피", 5000, "icecoffee");
        Store store = Store.makeStore(new StoreName("안녕커피"), new LocationName("합정역점"));
        Product product = Product.makeProductWith(store, new ProductName(request.getProductName()), new ProductPrice(request.getProductPrice()));
        ProductResponse response = ProductResponse.from(product);
        when(productIntegration.addProduct(any(), any()))
                .thenReturn(response);

        // when // then
        mockMvc
                .perform(post("/api/v1/stores/" + 1 + "/products")
                        .contentType(APPLICATION_JSON)
                        .content(om.writeValueAsString(request))
                )
                .andDo(print())
                .andExpect(status().is(CREATED.value()))
                .andExpect(jsonPath("$.data.productName").value("시원한 커피"))
                .andExpect(jsonPath("$.data.productPrice").value(5000));
    }
}