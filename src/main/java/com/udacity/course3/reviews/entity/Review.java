package com.udacity.course3.reviews.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotNull(message = "Review text cannot be empty")
    private String reviewText;
    @NotNull(message = "Review title cannot be empty")
    private String title;
    @ManyToOne
    private Product product;

    public Review(){

    }
    public Review(Integer id) {
        this.id = id;
    }

    public Review(String title, String reviewText){
        this.title = title;
        this.reviewText = reviewText;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
