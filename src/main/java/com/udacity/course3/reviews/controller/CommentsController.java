package com.udacity.course3.reviews.controller;

import com.udacity.course3.reviews.entity.Comment;
import com.udacity.course3.reviews.entity.Review;
import com.udacity.course3.reviews.repository.CommentRepository;
import com.udacity.course3.reviews.repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * Spring REST controller for working with comment entity.
 */
@RestController
@RequestMapping("/comments")
public class CommentsController {

    private ReviewRepository reviewRepository;
    private CommentRepository commentRepository;

    public CommentsController(ReviewRepository reviewRepository, CommentRepository commentRepository) {
        this.reviewRepository = reviewRepository;
        this.commentRepository = commentRepository;
    }
    // TODO: Wire needed JPA repositories here

    /**
     * Creates a comment for a review.
     * <p>
     * 1. Add argument for comment entity. Use {@link RequestBody} annotation.
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, save comment.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.POST)
    public ResponseEntity<?> createCommentForReview(@PathVariable("reviewId") Integer reviewId, @Valid @RequestBody Comment comment) {
        Optional<Review> reviewResource = reviewRepository.findById(reviewId);
        if (reviewResource.isPresent()) {
            comment.setReview(reviewResource.get());
            return ResponseEntity.ok(commentRepository.save(comment));
        }
        return ResponseEntity.notFound().build();
    }

    /**
     * List comments for a review.
     * <p>
     * 2. Check for existence of review.
     * 3. If review not found, return NOT_FOUND.
     * 4. If found, return list of comments.
     *
     * @param reviewId The id of the review.
     */
    @RequestMapping(value = "/reviews/{reviewId}", method = RequestMethod.GET)
    public ResponseEntity<List<Comment>> listCommentsForReview(@PathVariable("reviewId") Integer reviewId) {
        Optional<Review> review = reviewRepository.findById(reviewId);
        if (review.isPresent()) {
            return ResponseEntity.ok(commentRepository.findAllByReview(new Review(reviewId)));
        }
        return ResponseEntity.notFound().build();
    }
}