package com.example.applaudo.nasaapp.network;

import com.example.applaudo.nasaapp.models.PhotoRoot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    String BASE_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/";

    @GET("photos")
        //Setting the params for the URL
    Call<PhotoRoot> getPhotoRoot(@Query("sol") int sol, @Query("page") int page, @Query("api_key") String api_key);

}
