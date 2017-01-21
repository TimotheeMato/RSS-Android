package com.timotheemato.rssfeedaggregator.network.services;

import com.timotheemato.rssfeedaggregator.network.interfaces.ILoginService;
import com.timotheemato.rssfeedaggregator.network.models.LoginRequest;
import com.timotheemato.rssfeedaggregator.network.models.LoginResponse;
import com.timotheemato.rssfeedaggregator.network.models.SimpleResponse;

import java.net.HttpRetryException;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by tmato on 1/21/17.
 */

public class LoginService {

    private ILoginService loginService;
    private boolean isRequestingInformation = false;

    public LoginService(Retrofit retrofit) {
        this.loginService = retrofit.create(ILoginService.class);
    }

    public Completable register(LoginRequest request) {
        return loginService.register(request)
                .doOnSubscribe(disposable -> isRequestingInformation = true)
                .doOnTerminate(() -> isRequestingInformation = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements();
    }

    public Completable unregister(String authorization) {
        return loginService.unregister(authorization)
                .doOnSubscribe(disposable -> isRequestingInformation = true)
                .doOnTerminate(() -> isRequestingInformation = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements();
    }

    public Observable<LoginResponse> login(LoginRequest request) {
        return loginService.login(request)
                .doOnSubscribe(disposable -> isRequestingInformation = true)
                .doOnTerminate(() -> isRequestingInformation = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public boolean isRequestingInformation() {
        return isRequestingInformation;
    }
}
