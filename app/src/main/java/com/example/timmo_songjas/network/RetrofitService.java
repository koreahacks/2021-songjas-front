package com.example.timmo_songjas.network;

import com.example.timmo_songjas.data.ProfileEditInputData;
import com.example.timmo_songjas.data.ProfileEditInputResponse;
import com.example.timmo_songjas.data.ProfileEditResponse;
import com.example.timmo_songjas.data.SigninData;
import com.example.timmo_songjas.data.SigninResponse;
import com.example.timmo_songjas.data.SignupData;
import com.example.timmo_songjas.data.SignupResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

public interface RetrofitService {
    @POST("/users/signin")
    Call<SigninResponse> userSignin(@Body SigninData data);

    @POST("/users/signup")
    Call<SignupResponse> userSignup(@Body SignupData data);


    //프로필 조회
    @GET("/users")
    Call<ProfileEditResponse> userProfile(@Header("Authorization") String token, @Header("Content-Type") String type);

    //프로필 수정
    @PATCH("/users")
    Call<ProfileEditInputResponse> profileEdit(@Header("Authorization") String token, @Body ProfileEditInputData data);


}
