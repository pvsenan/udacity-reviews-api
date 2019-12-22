package com.udacity.course3.reviews.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Comment text cannot be empty")
    private String commentText;

    @ManyToOne
    private Review review;

    public Comment() {
    }

    public Comment(String commentText){
        this.commentText = commentText;
    }
    public Comment(Integer id){
        this.id = id;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
