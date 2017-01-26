package com.timotheemato.rssfeedaggregator.network.interfaces;

import com.timotheemato.rssfeedaggregator.network.models.LoginResponse;
import com.timotheemato.rssfeedaggregator.network.models.SimpleResponse;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by tmato on 1/21/17.
 */

public interface ILoginService {

    @FormUrlEncoded
    @POST("register")
    Observable<SimpleResponse> register(@Field("email") String email, @Field("password") String password);

    @FormUrlEncoded
    @POST("unregister")
    Observable<SimpleResponse> unregister(@Header("Authorization") String authorization);

    @FormUrlEncoded
    @POST("login")
    Observable<LoginResponse> login(@Field("email") String email, @Field("password") String password);
}
