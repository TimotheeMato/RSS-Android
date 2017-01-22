package com.timotheemato.rssfeedaggregator.network.interfaces;

import com.timotheemato.rssfeedaggregator.network.models.Feed;
import com.timotheemato.rssfeedaggregator.network.models.SimpleResponse;
import com.timotheemato.rssfeedaggregator.network.models.Subscription;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by tmato on 1/21/17.
 */

public interface IFeedService {
    @GET("feed")
    Observable<Feed> getFeed(@Query("limit") Integer limit, @Query("offset") Integer offset);

    @GET("subscriptions")
    Observable<List<Subscription>> getSubscriptions(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("subscribe")
    Observable<Subscription> subscribe(@Header("Authorization") String authorization, @Field("url") String url);

    @FormUrlEncoded
    @POST("unsubscribe/{id}")
    Observable<SimpleResponse> unsubscribe(@Header("Authorization") String authorization, @Path("id") Integer id);
}
