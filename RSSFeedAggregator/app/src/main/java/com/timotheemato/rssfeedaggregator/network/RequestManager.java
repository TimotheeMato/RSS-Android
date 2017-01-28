package com.timotheemato.rssfeedaggregator.network;

import android.content.Context;

import com.timotheemato.rssfeedaggregator.BuildConfig;
import com.timotheemato.rssfeedaggregator.network.models.ErrorResponse;
import com.timotheemato.rssfeedaggregator.network.models.LoginResponse;
import com.timotheemato.rssfeedaggregator.network.models.Post;
import com.timotheemato.rssfeedaggregator.network.models.SimpleResponse;
import com.timotheemato.rssfeedaggregator.network.models.Subscription;
import com.timotheemato.rssfeedaggregator.network.services.FeedService;
import com.timotheemato.rssfeedaggregator.network.services.LoginService;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by tmato on 1/21/17.
 */

public class RequestManager {
    private static RequestManager instance;

    private LoginService loginService;
    private FeedService feedService;

    private Retrofit retrofit;
    private Converter<ResponseBody, ErrorResponse> errorConverter;

    private RequestManager(Context context) {
        retrofit = getAdapter();
        errorConverter =
                retrofit.responseBodyConverter(ErrorResponse.class, new Annotation[0]);

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

    private Retrofit getAdapter() {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        return new Retrofit.Builder()
                .baseUrl(BuildConfig.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
    }

    public Observable<SimpleResponse> register(String email, String password) {
        return loginService.register(email, password);
    }

    public Observable<SimpleResponse> unregister(String authorization) {
        return loginService.unregister(authorization);
    }

    public Observable<LoginResponse> login(String email, String password) {
        return loginService.login(email, password);
    }

    public Observable<List<Subscription>> getSubscriptions(String authorization) {
        return feedService.getSubscriptions(authorization);
    }

    public Observable<Subscription> subscribe(String authorization, String url) {
        return feedService.subscribe(authorization, url);
    }

    public Observable<List<Post>> getFeed(String authorization, Integer id, Integer limit, Integer offset) {
        return feedService.getFeed(authorization, id, limit, offset);
    }

    public Observable<SimpleResponse> read(String authorization, Integer id) {
        return feedService.read(authorization, id);
    }

    public Observable<SimpleResponse> unsubscribe(String authorization, Integer id) {
        return feedService.unsubscribe(authorization, id);
    }

    public ErrorResponse getError(Response response) {
        try {
            return errorConverter.convert(response.errorBody());
        } catch (IOException e) {
            return null;
        }
    }
}