package com.example.ambu.utils.Interfaces;

import com.example.ambu.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {

    @POST("/login")
    Call<Void> login(@Body User usuario);
}
