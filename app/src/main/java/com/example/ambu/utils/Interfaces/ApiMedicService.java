package com.example.ambu.utils.Interfaces;

import com.example.ambu.models.Diagnosis;
import com.example.ambu.models.Issue;
import com.example.ambu.models.Symptom;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiMedicService {
    @GET("/symptoms")
    Call<List<Symptom>> getSymptomsByIds(@Query("token") String token, @Query("format") String format, @Query("language") String language, @Query("symptoms") String symptoms);

    @GET("/symptoms")
    Call<List<Symptom>> getAllSymptoms(@Query("token") String token, @Query("format") String format, @Query("language") String language);

    @POST("/login")
    Call<ResponseBody> loginApiMedic(@Header("Authorization") String authorizationApiMedic);

    @GET("/diagnosis")
    Call<List<Diagnosis>> getDiagnosis(@Query("token") String token, @Query("format") String format, @Query("language") String language, @Query("Gender") String gender, @Query("year_of_birth") String year, @Query("symptoms") String symptoms);


    @GET("/issues")
    Call<ArrayList<Issue>> getAllIssues(@Query("token") String token,  @Query("language") String language,@Query("format") String format);



    @GET("/issues")
    Call<ArrayList<Issue>> getAllIssuesbyid(@Query("token") String token,@Query("issue_id") String issue_id,  @Query("language") String language,@Query("format") String format);


}



