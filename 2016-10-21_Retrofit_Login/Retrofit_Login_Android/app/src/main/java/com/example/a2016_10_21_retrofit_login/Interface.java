package com.example.a2016_10_21_retrofit_login;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by MyndDev on 10/21/2016.
 */

public interface Interface {
    //This method is used for "POST"
    @FormUrlEncoded
    @POST("/api.php")
    void postData(@Field("method") String method,
                  @Field("username") String username,
                  @Field("password") String password,
                  Callback<ServerResponse> serverResponseCallback);

    //This method is used for "GET"
    @GET("/api.php")
    void getData(@Query("method") String method,
                 @Query("username") String username,
                 @Query("password") String password,
                 Callback<ServerResponse> serverResponseCallback);
}
