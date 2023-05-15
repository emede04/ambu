package com.example.ambu.utils.Interfaces;

import com.example.ambu.models.Symptom;
import com.example.ambu.models.User;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface apiMedicService {
    ArrayList <Symptom> listaS = null;

    @GET("/symptoms")
    Call<List<Symptom>> getSymptomsByIds(@Query("token") String token, @Query("format") String format, @Query("language") String language, @Query("symptoms") String symptoms);

    @GET("/symptoms")
     Call<List<Symptom>> getAllSymptoms(@Query("token") String token, @Query("format") String format, @Query("language") String language);

    @POST("/login")
    Call<ResponseBody> loginApiMedic(@Header("Authorization") String authorizationApiMedic);




    }



