package com.example.login_reg;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public interface MovieApi {
    @GET("/movies?filter=new")
    Call<List<Movie>> getData();

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://cinema.areas.su/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
