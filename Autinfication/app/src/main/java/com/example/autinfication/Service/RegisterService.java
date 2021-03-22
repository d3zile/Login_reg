package com.example.autinfication.Service;

import com.example.autinfication.Request.RegisterRequest;
import com.example.autinfication.Response.RegisterResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RegisterService {
    @POST("auth/register")
    Call<RegisterResponse> registerUser(@Body RegisterRequest registerRequest);

}
