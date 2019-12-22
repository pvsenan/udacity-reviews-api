package com.udacity.course3.reviews.jpatests;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@RunWith(SpringRunner.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
public class ReviewRepositoryTests {
    @Autowired
    EntityManager entityManager;
    @Autowired
    ReviewRepository reviewRepository;

    @Test
    public void addReviewToProduct(){
        Product product = new Product("Arla Ko", "Best Swedish milk");
        Review review = new Review("Best Brand","Best Milk in the market");
        review.setProduct(product);
        entityManager.persist(product);
        entityManager.persist(review);
        reviewRepository.save(review);
    }

    @Test
    public void findReviewById(){
        Product product = new Product("Arla Ko", "Best Swedish milk");
        Review review = new Review("Best Brand","Best Milk in the market");
        review.setProduct(product);
        entityManager.persist(product);
        entityManager.persist(review);
        reviewRepository.save(review);

        Optional<Review> reviewResult = reviewRepository.findById(1);
        Assert.assertNotNull(reviewResult);
        Assert.assertEquals("Best Brand", reviewResult.get().getTitle());
    }

    @Test
    public void findAllReviewsByProduct(){
        Product product = new Product("Arla Ko", "Best Swedish milk");
        Review review1 = new Review("Best Brand","Best Milk in the market");
        Review review2 = new Review("Too Expensive","Best Milk but too expensive");
        review1.setProduct(product);
        review2.setProduct(product);
        entityManager.persist(product);
        entityManager.persist(review1);
        entityManager.persist(review2);
        reviewRepository.save(review1);
        reviewRepository.save(review2);

        Optional<List<Review>> reviewResult = Optional.ofNullable(reviewRepository.findAllByProduct(product));
        Assert.assertNotNull(reviewResult);
        Assert.assertEquals(2, reviewResult.get().size());
    }
}
