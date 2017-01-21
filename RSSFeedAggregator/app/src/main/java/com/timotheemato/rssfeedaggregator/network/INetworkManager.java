package com.timotheemato.rssfeedaggregator.network;

import java.util.Observable;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by tmato on 1/21/17.
 */

public interface INetworkManager {
    @POST("register")
    Observable<SimpleReponse> register(@Body LoginRequest loginRequest);

    @POST("unregister")
    Observable<SimpleReponse> unregister(@Header("Authorization") String authorization);

    @POST("login")
    Observable<LoginReponse> login(@Body LoginRequest loginRequest);

    @GET("feed")
    Observable<Feed> getFeed(@Query("limit") Integer limit, @Query("offset") Integer offset);

    @GET("subscriptions")
    Observable<List<Subscription>> getSubscriptions(@Header("Authorization") String authorization);

    @POST("subscribe")
    Observable<Subscription> subscribe(@Header("Authorization") String authorization, @Body String url);

    @POST("unsubscribe/{id}")
    Observable<SimpleReponse> subscribe(@Header("Authorization") String authorization, @Path("id") Integer id);
}
