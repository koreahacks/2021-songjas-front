package com.example.timmo_songjas.network;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {

    private final static String BASE_URL = "http://115.85.183.231:3000";//"할당받은 public dns 작성";
    //baseUrl 반드시 '/'로 마무리 해야함
    private static Retrofit retrofit = null;
    private static OkHttpClient okhttp = null;

    private RetrofitClient() {
    }

    public static Retrofit getClient() {

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().serializeNulls().create()))                    //jason을 파싱 위한 Gson 변환기 등록
                    .build();
        }
        return retrofit;
    }

}
