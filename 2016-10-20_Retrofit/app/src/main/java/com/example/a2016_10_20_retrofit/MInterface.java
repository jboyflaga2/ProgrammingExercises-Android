package com.example.a2016_10_20_retrofit;

import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by MyndDev on 10/20/2016.
 */

public interface MInterface {
    @GET("/users/jboyflaga")
    void getUser(Callback<Pojo> uscb);
}
