package com.example.populermoviestage1.review;

import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class ReviewApiClient {
    private static ReviewApiClient.ReviewInterface reviewInterface;
    private static String Base_Url="https://api.themoviedb.org/3/movie/";

    private ReviewApiClient() {
    }


    public static ReviewApiClient.ReviewInterface getReviewInterface() {

        if (reviewInterface == null) {
            reviewInterface = new retrofit2.Retrofit.Builder()
                    .baseUrl(Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(ReviewApiClient.ReviewInterface.class);
        }
        return reviewInterface;
    }

    public interface ReviewInterface {
        @GET("{movieID}/reviews")
        Call<ReviewList> getReviews(@Path("movieID") int movieID, @Query("api_key") String apiKey);

    }

}
