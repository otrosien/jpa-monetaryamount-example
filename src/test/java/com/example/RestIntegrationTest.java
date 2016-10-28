package com.example;

import static org.hamcrest.CoreMatchers.is;
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

    @Before
    public void setup() {
        mockMvc = webAppContextSetup(ctx).build();
    }

    @Test
    public void should_test_product_via_rest() throws Exception {
        MvcResult result = mockMvc
                .perform(post("/products")
                        .content("{\"currency\":\"EUR\", \"price\": {\"amount\": 1.23, \"currency\":\"EUR\" }}")
                        .contentType("application/json"))
        .andDo(print())
        .andExpect(status().isCreated())
        .andReturn();
        URI location = URI.create(result.getResponse().getHeader("Location"));

        result = mockMvc.perform(get(location))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("currency", is("EUR")))
        .andExpect(jsonPath("price.amount", is(1.23)))
        .andExpect(jsonPath("price.currency", is("EUR")))
        .andReturn();

        result = mockMvc.perform(put(location).content("{\"currency\":\"GBP\", \"price\": {\"amount\": 1.24, \"currency\":\"GBP\" }}"))
        .andDo(print())
        .andExpect(status().isNoContent())
        .andReturn();

        result = mockMvc.perform(get(location))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("currency", is("GBP")))
        .andExpect(jsonPath("price.amount", is(1.24)))
        .andExpect(jsonPath("price.currency", is("GBP")))
        .andReturn();

        result = mockMvc.perform(patch(location).content("{\"currency\":\"USD\", \"price\": {\"amount\": 1.25, \"currency\":\"USD\" }}"))
        .andDo(print())
        .andExpect(status().isNoContent())
        .andReturn();

        result = mockMvc.perform(get(location))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(jsonPath("currency", is("USD")))
        .andExpect(jsonPath("price.amount", is(1.25)))
        .andExpect(jsonPath("price.currency", is("USD")))
        .andReturn();

    }
}
