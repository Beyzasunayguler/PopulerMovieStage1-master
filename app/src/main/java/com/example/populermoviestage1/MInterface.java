package com.example.populermoviestage1;



import retrofit2.Call;
import retrofit2.http.GET;

public interface MInterface {

    @GET("https://api.themoviedb.org/3/movie/popular?movie?sort_by=popularity.desc&api_key=c9a15d538bdf660ff7bfc99552c95c0b")
    Call<MovieData> getPopularMovies();


    @GET("https://api.themoviedb.org/3/movie/top_rated?movie?sort_by=vote_average.desc&api_key=c9a15d538bdf660ff7bfc99552c95c0b")
    Call<MovieData> getHighestRated();

}
