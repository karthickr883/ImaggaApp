package com.example.karthick.imaggaapi;

import java.io.File;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface imaggaInterface {

    //@GET("tags")
    //Call<>
    @GET("tags")
    Call<Example> getResponse(@Query("user") String apiKey, @Query("image_url") String url);

    @Headers("Authorization: Basic YWNjX2YzZmQ1YWQxYWM0NjQ2NDoyNzhkMDdlNTNmMzQ0YWNiNTczMjE2NTBjOGUwMTkzZQ==")
    @GET("tags")
    Call<Example> getRp(@Query("image_url") String url);

    @Headers("Authorization: Basic YWNjX2YzZmQ1YWQxYWM0NjQ2NDoyNzhkMDdlNTNmMzQ0YWNiNTczMjE2NTBjOGUwMTkzZQ==")
    @GET("uploads")
    Call<ImgResponse> getImg(@Query("image") File path);

    @Headers("Authorization: Basic YWNjX2YzZmQ1YWQxYWM0NjQ2NDoyNzhkMDdlNTNmMzQ0YWNiNTczMjE2NTBjOGUwMTkzZQ==")
    @Multipart
    @POST("uploads")
    Call<ImgResponse> getI(@Part MultipartBody.Part file);
}
