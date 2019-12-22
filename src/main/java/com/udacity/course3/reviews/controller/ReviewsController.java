package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Product;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.ProductRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with review entity.
 */
@RestController
public class ReviewsController {

    private ReviewRepository reviewRepository;
    private ProductRepository productRepository;

    public ReviewsController(ReviewRepository reviewRepository, ProductRepository productRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
    }
    // TODO: Wire JPA repositories here

    /**
     * Creates a review for a product.
     * <p>
     * 1. Add argument for review entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of product.
     * 3. If product not found, return NOT_FOUND.
     * 4. If found, save review.
     *
     * @param productId The id of the product.
     * @return The created review or 404 if product id is not found.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.POST)
    public ResponseEntity<Review> createReviewForProduct(@PathVariable("productId") Integer productId, @Valid @RequestBody Review review) {
        Optional<Product> resource = productRepository.findById(productId);
        if(resource.isPresent()){
            review.setProduct(resource.get());
            reviewRepository.save(review);
            return ResponseEntity.ok(reviewRepository.save(review));
        }

        return ResponseEntity.notFound().build();
    }

    /**
     * Lists reviews by product.
     *
     * @param productId The id of the product.
     * @return The list of reviews.
     */
    @RequestMapping(value = "/reviews/products/{productId}", method = RequestMethod.GET)
    public ResponseEntity<List<Review>> listReviewsForProduct(@PathVariable("productId") Integer productId) {
        return ResponseEntity.ok(reviewRepository.findAllByProduct(new Product(productId)));
    }
}