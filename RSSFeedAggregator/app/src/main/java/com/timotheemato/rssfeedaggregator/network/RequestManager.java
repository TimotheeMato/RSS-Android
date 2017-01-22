package com.timotheemato.rssfeedaggregator.network;

import android.content.Context;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.timotheemato.rssfeedaggregator.network.models.LoginResponse;
import com.timotheemato.rssfeedaggregator.network.models.SimpleResponse;
import com.timotheemato.rssfeedaggregator.network.services.FeedService;
import com.timotheemato.rssfeedaggregator.network.services.LoginService;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.MaybeSource;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tmato on 1/21/17.
 */

public class RequestManager {
    private static RequestManager instance;

    private LoginService loginService;
    private FeedService feedService;

    private RequestManager(Context context) {
        Retrofit retrofit = getAdapter();

        this.loginService = new LoginService(retrofit);
        this.feedService = new FeedService(retrofit);
    }

    public static RequestManager getInstance(Context context) {
        synchronized (RequestManager.class) {
            if (instance == null) {
                instance = new RequestManager(context);
            }
            return instance;
        }
    }

    public boolean isRequestingInformation() {
        return loginService.isRequestingInformation() ||
                feedService.isRequestingInformation();
    }

    public static Retrofit getAdapter() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        return new Retrofit.Builder()
                .baseUrl("http://ninjarss-env.eu-central-1.elasticbeanstalk.com/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    public Maybe<SimpleResponse> register(String email, String password) {
        return loginService.register(email, password);
    }

    public Completable unregister(String authorization) {
        return loginService.unregister(authorization);
    }

    public MaybeSource<LoginResponse> login(String email, String password) {
        return loginService.login(email, password);
    }
}
