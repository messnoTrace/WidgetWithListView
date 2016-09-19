package com.notrace.api;


import com.notrace.model.NewsResponse;
import com.notrace.model.TokenResponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

/**
 * Created by notrace on 2016/9/13.
 */
public interface TouTiaoApi {
    @Headers({
            "Content-Type:application/x-www-form-urlencoded"
    })
    @POST("/auth/access/device/")
     Call<TokenResponse>getNewToken();
//
//    @Headers({
//            "Content-Type:application/x-www-form-urlencoded"
//    })
    @FormUrlEncoded
    @POST("auth/access/device/")
    Observable<TokenResponse>getToken(@FieldMap HashMap<String,String>params);

    @GET("/data/stream/v3")
    Observable<NewsResponse>getNews(@Query("access_token") String token,@QueryMap HashMap<String,String>params);
}
