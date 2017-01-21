package com.timotheemato.rssfeedaggregator.network.interfaces;

import com.timotheemato.rssfeedaggregator.network.models.LoginRequest;
import com.timotheemato.rssfeedaggregator.network.models.LoginResponse;
import com.timotheemato.rssfeedaggregator.network.models.SimpleResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by tmato on 1/21/17.
 */

public interface ILoginService {
    @POST("register")
    Observable<SimpleResponse> register(@Body LoginRequest loginRequest);

    @POST("unregister")
    Observable<SimpleResponse> unregister(@Header("Authorization") String authorization);

    @POST("login")
    Observable<LoginResponse> login(@Body LoginRequest loginRequest);
}
