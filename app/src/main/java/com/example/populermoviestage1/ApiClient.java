package com.example.populermoviestage1;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    private static Retrofit retrofit=null;
    private static String Base_Url="https://api.themoviedb.org/3/discover/";

    public static Retrofit getClient(){
        if (retrofit==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Base_Url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(new OkHttpClient())
                    .build();

            return retrofit;
        }
        return retrofit;
    }


}
