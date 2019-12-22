package com.udacity.course3.reviews.controller;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
public class ReviewsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private JacksonTester<Product> jsonProduct;

    @Autowired
    private JacksonTester<Review> jsonReview;

    private Product product;
    private Review review;

    @Before
    public void setUp() throws Exception{
        product = new Product(1);
        product.setName("Arla Ko");
        product.setDescription("Best swedish milk");
        mockMvc.perform(
                post(new URI("/products/"))
                        .content(jsonProduct.write(product).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());
    }
    @Test
    public void shouldBeAbleToCreateReviewForExistingProducts() throws Exception{
        review = new Review(1);
        review.setTitle("The best milk in Sweden");
        review.setReviewText("This the best ecological milk you can get here");
        MvcResult response1 = postReview(review);
        Assert.assertEquals(HttpStatus.OK.value(),response1.getResponse().getStatus());

    }

    @Test
    public void shouldGetNotFoundForNonExistingProducts() throws Exception{
        review = new Review(1);
        review.setTitle("The best milk in Sweden");
        review.setReviewText("This the best ecological milk you can get here");
        mockMvc.perform(
                post(new URI("/reviews/products/4/"))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jsonReview.write(review).getJson())
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldBeAbleToAddMultipleReviewsForSameProduct() throws Exception{
        review = new Review(1);
        review.setTitle("The best milk in Sweden");
        review.setReviewText("This the best ecological milk you can get here");
        MvcResult response1 = postReview(review);
        Assert.assertEquals(HttpStatus.OK.value(),response1.getResponse().getStatus());

        Review review2 = new Review(2);
        review2.setTitle("Too expensive");
        review2.setReviewText("This milk is too expensive compared to similar brands");
        MvcResult response2 = postReview(review2);
        Assert.assertEquals(HttpStatus.OK.value(),response2.getResponse().getStatus());
    }
    @Test
    public void shouldListAllReviewsForProduct() throws Exception {
        review = new Review(1);
        review.setTitle("The best milk in Sweden");
        review.setReviewText("This the best ecological milk you can get here");
        postReview(review);

        Review review2 = new Review(2);
        review2.setTitle("Too expensive");
        review2.setReviewText("This milk is too expensive compared to similar brands");
        postReview(review2);

        MvcResult result =  mockMvc.perform(
                get(new URI("/reviews/products/1/"))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jsonReview.write(review).getJson())
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        DocumentContext jsonDoc = JsonPath.parse(result.getResponse().getContentAsString());
        List<String> reviewTitles = JsonPath.read(jsonDoc.json().toString(),"$..title");
        Assert.assertEquals("The best milk in Sweden",reviewTitles.get(0));
        Assert.assertEquals("Too expensive",reviewTitles.get(1));
    }

    private MvcResult postReview(Review review) throws Exception {
      MvcResult result =  mockMvc.perform(
                post(new URI("/reviews/products/1/"))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jsonReview.write(review).getJson())
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
      return result;
    }
}