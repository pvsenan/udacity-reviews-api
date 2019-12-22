package com.udacity.course3.reviews.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.udacity.course3.reviews.entity.Product;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URI;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
public class ProductsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private JacksonTester<Product> json;

    private Product product1;
    private Product product2;

    @Before
    public void setUp() {
        product1 = new Product(1);
        product1.setName("Arla Ko");
        product1.setDescription("Best swedish milk");

        product2 = new Product();
        product2.setName("Snickers");
        product2.setDescription("You are not you when you are hungry");
    }

    @Test
    public void shouldBeAbleToCreateProduct() throws Exception {
        mockMvc.perform(
                post(new URI("/products/"))
                        .content(json.write(product1).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }

    @Test
    public void shouldBeAbleToFindOneProdctById() throws Exception {
        mockMvc.perform(
                post(new URI("/products/"))
                        .content(json.write(product1).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
        MvcResult result = mockMvc.perform(
                get(new URI("/products/1")))
                .andReturn();
        Assert.assertNotNull(result.getResponse().getContentAsString());
    }

    @Test
    public void shouldBeAbleToListAllTheProducts() throws Exception{
        mockMvc.perform(
                post(new URI("/products/"))
                        .content(json.write(product1).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
        mockMvc.perform(
                post(new URI("/products/"))
                        .content(json.write(product2).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());

        MvcResult result = mockMvc.perform(
                get(new URI("/products/")))
                .andReturn();
        DocumentContext jsonDoc = JsonPath.parse(result.getResponse().getContentAsString());
        List<String> productIds = JsonPath.read(jsonDoc.json().toString(),"$..id");
        Assert.assertEquals(2,productIds.size());
    }
}