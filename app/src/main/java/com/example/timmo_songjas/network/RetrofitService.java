package com.example.timmo_songjas.network;

import com.example.timmo_songjas.data.EmailAuthData;
import com.example.timmo_songjas.data.EmailAuthResponse;
import com.example.timmo_songjas.data.MemberDetailResponse;
import com.example.timmo_songjas.data.MessageTimgleData;
import com.example.timmo_songjas.data.MessageTimgleResponse;
import com.example.timmo_songjas.data.PartyFindResponse;
import com.example.timmo_songjas.data.SigninData;
import com.example.timmo_songjas.data.SigninResponse;
import com.example.timmo_songjas.data.SignupData;
import com.example.timmo_songjas.data.SignupResponse;
import com.example.timmo_songjas.data.TimgleListResponse;
import com.example.timmo_songjas.data.TimmoListResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RetrofitService {
    @POST("/users/signin")
    Call<SigninResponse> userSignin(@Body SigninData data);

    @POST("/users/signup")
    Call<SignupResponse> userSignup(@Body SignupData data);

    @POST("/auth/email")
    Call<EmailAuthResponse> emailAuth(@Body EmailAuthData data);

    //팀원 팀글 이력서 등록
    @FormUrlEncoded
    @PATCH("/projects/members")
    Call<MessageTimgleResponse> messageTimgleList(@Body MessageTimgleData data);

    //팀모 선택 목록 조회 , 팀빌딩채딩 팀모 리스트 불러오기기
    @GET("/users/projects")
    Call<TimmoListResponse> userTimmoList(@Header("Authorization") String token);


    //팀협업, 팀원이 팀글 등록하고나서
    @GET("/users/members")
    Call<TimgleListResponse> userTigleList(@Header("Authorization") String token);//, @Header("Content-Type") String type

    @GET("/users/{email}")
    Call<PartyFindResponse> partyFind(@Header("Authorization") String token, @Header("Content-type") String type, @Path("email") String email);

    //팀글 조회
    @GET("/members/{id}")
    Call<MemberDetailResponse> memberDetail(@Header("Authorization") String token, @Header("Content-type") String type, @Path("id") int id);


}
