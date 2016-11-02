package com.example;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.springframework.data.rest.webmvc.RestMediaTypes.JSON_PATCH_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestIntegrationTest {

    @Autowired
    private WebApplicationContext ctx;

    private MockMvc mockMvc;

    private URI location;

    @Before
    public void setup() throws Exception {
        mockMvc = webAppContextSetup(ctx).build();
        location = createProduct();
    }

    @Test
    public void should_get() throws Exception {
        mockMvc.perform(get(location))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("currency", equalTo("EUR")))
        .andExpect(jsonPath("price.amount", equalTo(1.23)))
        .andExpect(jsonPath("price.currency", equalTo("EUR")))
        .andReturn();
    }

    @Test
    public void should_put() throws Exception {
        mockMvc.perform(put(location).content("{\"currency\":\"GBP\", \"price\": {\"amount\": 1.24, \"currency\":\"GBP\" }}"))
        .andDo(print())
        .andExpect(status().isNoContent())
        .andReturn();

        mockMvc.perform(get(location))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("currency", equalTo("GBP")))
        .andExpect(jsonPath("price.amount", equalTo(1.24)))
        .andExpect(jsonPath("price.currency", equalTo("GBP")))
        .andReturn();
    }

    @Test
    public void should_patch_simple() throws Exception {
        mockMvc.perform(patch(location).content("{\"currency\":\"USD\", \"price\": {\"amount\": 1.25, \"currency\":\"USD\" }}"))
        .andDo(print())
        .andExpect(status().isNoContent())
        .andReturn();

        mockMvc.perform(get(location))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("currency", equalTo("USD")))
        .andExpect(jsonPath("price.amount", equalTo(1.25)))
        .andExpect(jsonPath("price.currency", equalTo("USD")))
        .andReturn();
    }

    @Test
    public void should_patch_with_json_patch() throws Exception {
        mockMvc.perform(patch(location)
                .content("[{\"op\":\"replace\",\"path\":\"/price\",\"value\":{\"amount\":\"1.26\", \"currency\":\"CHF\"}},"
                        + "{\"op\":\"replace\",\"path\":\"/currency\",\"value\":\"CHF\"}]")
                .contentType(JSON_PATCH_JSON))
        .andDo(print())
        .andExpect(status().isNoContent())
        .andReturn();

        mockMvc.perform(get(location))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("currency", equalTo("CHF")))
        .andExpect(jsonPath("price.amount", equalTo(1.26)))
        .andExpect(jsonPath("price.currency", equalTo("CHF")))
        .andReturn();
    }

    private URI createProduct() throws Exception {
        MvcResult result = mockMvc
                .perform(post("/products")
                        .content("{\"currency\":\"EUR\", \"price\": {\"amount\": 1.23, \"currency\":\"EUR\" }}")
                        .contentType("application/json"))
        .andDo(print())
        .andExpect(status().isCreated())
        .andReturn();
        URI location = URI.create(result.getResponse().getHeader("Location"));
        return location;
    }
}
