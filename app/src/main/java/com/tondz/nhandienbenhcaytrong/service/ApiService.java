package com.tondz.nhandienbenhcaytrong.service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {
    String BASE_URL = "http://192.168.1.110:5000";
    Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy").create();
    OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS).build();
    ApiService api = new Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build().create(ApiService.class);

    @Multipart
    @POST("/api/la-cam")
    Call<ResponseBody> predictLaCam(@Part  MultipartBody.Part image);

    @Multipart
    @POST("/api/qua-cam")
    Call<ResponseBody> predictQuaCam(@Part MultipartBody.Part image);

    @Multipart
    @POST("/api/cay-que")
    Call<ResponseBody> predictCayQue(@Part MultipartBody.Part image);
}
