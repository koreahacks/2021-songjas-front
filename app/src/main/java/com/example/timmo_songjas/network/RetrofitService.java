package com.example.timmo_songjas.network;

import com.example.timmo_songjas.data.SigninData;
import com.example.timmo_songjas.data.SigninResponse;
import com.example.timmo_songjas.data.SignupData;
import com.example.timmo_songjas.data.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface RetrofitService {
    @POST("/users/signin")
    Call<SigninResponse> userSignin(@Body SigninData data);

    @POST("/users/signup")
    Call<SignupResponse> userSignup(@Body SignupData data);


}
