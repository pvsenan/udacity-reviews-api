package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Comment;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.net.URI;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
public class CommentsControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private JacksonTester<Product> jsonProduct;

    @Autowired
    private JacksonTester<Review> jsonReview;

    @Autowired
    private JacksonTester<Comment> jsonComment;

    private Product product;
    private Review review;
    private Comment comment;

    @Before
    public void setUp() throws Exception {
        product = new Product(1);
        product.setName("Arla Ko");
        product.setDescription("Best swedish milk");

        review = new Review(1);
        review.setTitle("The best milk in Sweden");
        review.setReviewText("This the best ecological milk you can get here");

        mockMvc.perform(
                post(new URI("/products/"))
                        .content(jsonProduct.write(product).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isCreated());

        mockMvc.perform(
                post(new URI("/reviews/products/1/"))
                        .contentType(MediaType.APPLICATION_JSON_UTF8)
                        .content(jsonReview.write(review).getJson())
                        .accept(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldBeAbleToPostCommentsForReview() throws Exception {
        comment = new Comment(1);
        comment.setCommentText("Thank you. We are glad to hear your review");
        mockMvc.perform(
                post(new URI("/comments/reviews/1"))
                        .content(jsonComment.write(comment).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldBeAbleToListComments() throws Exception {
        comment = new Comment(1);
        comment.setCommentText("Thank you. We are glad to hear your review");
        MvcResult response = mockMvc.perform(
                post(new URI("/comments/reviews/1"))
                        .content(jsonComment.write(comment).getJson())
                        .contentType(MediaType.APPLICATION_JSON_UTF8))
                .andReturn();
        Assert.assertNotNull(response.getResponse().getContentAsString());
    }
}