package com.example.populermoviestage1.review;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewList {
    @SerializedName("results")
    public List<Review> reviews;

    public List<Review> getReviews(){
        return reviews;
    }
}
