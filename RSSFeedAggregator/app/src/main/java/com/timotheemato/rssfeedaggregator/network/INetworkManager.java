package com.timotheemato.rssfeedaggregator.network;

import com.timotheemato.rssfeedaggregator.models.Feed;
import com.timotheemato.rssfeedaggregator.models.LoginRequest;
import com.timotheemato.rssfeedaggregator.models.LoginResponse;
import com.timotheemato.rssfeedaggregator.models.SimpleResponse;
import com.timotheemato.rssfeedaggregator.models.Subscription;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by tmato on 1/21/17.
 */

public interface INetworkManager {
    @POST("register")
    Observable<SimpleResponse> register(@Body LoginRequest loginRequest);

    @POST("unregister")
    Observable<SimpleResponse> unregister(@Header("Authorization") String authorization);

    @POST("login")
    Observable<LoginResponse> login(@Body LoginRequest loginRequest);

    @GET("feed")
    Observable<Feed> getFeed(@Query("limit") Integer limit, @Query("offset") Integer offset);

    @GET("subscriptions")
    Observable<List<Subscription>> getSubscriptions(@Header("Authorization") String authorization);

    @POST("subscribe")
    Observable<Subscription> subscribe(@Header("Authorization") String authorization, @Body String url);

    @POST("unsubscribe/{id}")
    Observable<SimpleResponse> subscribe(@Header("Authorization") String authorization, @Path("id") Integer id);
}
