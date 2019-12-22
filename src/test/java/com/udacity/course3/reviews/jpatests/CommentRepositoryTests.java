package com.udacity.course3.reviews.jpatests;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
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
public class CommentRepositoryTests {
    @Autowired
    EntityManager entityManager;
    @Autowired
    ReviewRepository reviewRepository;
    @Autowired
    CommentRepository commentRepository;

    @Test
    public void addReviewToProduct(){
        Product product = new Product("Arla Ko", "Best Swedish milk");
        Review review = new Review("Best Brand","Best Milk in the market");
        Comment comment = new Comment("Thank you for the comment");
        review.setProduct(product);
        comment.setReview(review);
        entityManager.persist(product);
        entityManager.persist(review);
        commentRepository.save(comment);
    }

    @Test
    public void findCommentByReview(){
        Product product = new Product("Arla Ko", "Best Swedish milk");
        Review review = new Review("Best Brand","Best Milk in the market");
        Comment comment = new Comment("Thank you for the comment");
        review.setProduct(product);
        comment.setReview(review);
        entityManager.persist(product);
        entityManager.persist(review);
        entityManager.persist(comment);
        commentRepository.save(comment);

        Optional<List<Comment>> commentResult = Optional.ofNullable(commentRepository.findAllByReview(review));
        Assert.assertEquals("Thank you for the comment", commentResult.get().get(0).getCommentText());
    }

    @Test
    public void findCommentsById(){
        Product product = new Product("Arla Ko", "Best Swedish milk");
        Review review = new Review("Best Brand","Best Milk in the market");
        Comment comment = new Comment("Thank you for the comment");
        review.setProduct(product);
        comment.setReview(review);
        entityManager.persist(product);
        entityManager.persist(review);
        entityManager.persist(comment);
        reviewRepository.save(review);
        commentRepository.save(comment);

        Optional<Comment> commentResult = commentRepository.findById(1);
        Assert.assertEquals("Thank you for the comment",commentResult.get().getCommentText());
    }

    @Test
    public void findAllCommentsByReview(){
        Product product = new Product("Arla Ko", "Best Swedish milk");
        Review review = new Review("Best Brand","Best Milk in the market");
        Comment comment1 = new Comment("Thank you for the comment");
        Comment comment2 = new Comment("We are sorry to hear that");
        review.setProduct(product);
        comment1.setReview(review);
        comment2.setReview(review);
        entityManager.persist(product);
        entityManager.persist(review);
        entityManager.persist(comment1);
        entityManager.persist(comment2);
        commentRepository.save(comment1);
        commentRepository.save(comment2);

        Optional<List<Comment>> commentResult = Optional.ofNullable(commentRepository.findAllByReview(review));
        Assert.assertEquals("We are sorry to hear that", commentResult.get().get(1).getCommentText());
    }
}
