package com.example.populermoviestage1.Videos;


import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class VideosApiClient {
    private static VideosApiClient.VideosInterface videosInterface;
    private static String Base_Url="https://api.themoviedb.org/3/movie/";

    private VideosApiClient(){
    }


    public static VideosApiClient.VideosInterface getVideosInterface() {
        if (videosInterface == null) {
            videosInterface = new retrofit2.Retrofit.Builder()
                    .baseUrl(Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(VideosApiClient.VideosInterface.class);
        }
        return videosInterface;
    }

    public interface VideosInterface {
        @GET("{movieID}/videos")
        Call<VideosList> getVideos(@Path("movieID") int movieID, @Query("api_key") String apiKey);

    }
}
