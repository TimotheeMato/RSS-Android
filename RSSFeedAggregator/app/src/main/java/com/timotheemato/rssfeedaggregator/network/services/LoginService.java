package com.timotheemato.rssfeedaggregator.network.services;

import com.timotheemato.rssfeedaggregator.network.exceptions.NetworkGeneralException;
import com.timotheemato.rssfeedaggregator.network.exceptions.NetworkLoginException;
import com.timotheemato.rssfeedaggregator.network.exceptions.NetworkRegisterException;
import com.timotheemato.rssfeedaggregator.network.interfaces.ILoginService;
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

    public Completable register(String email, String password) {
        return loginService.register(email, password)
                .doOnSubscribe(disposable -> isRequestingInformation = true)
                .doOnTerminate(() -> isRequestingInformation = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(this::handleRegistrationError)
                .ignoreElements();
    }

    private Observable<SimpleResponse> handleRegistrationError(Throwable throwable) {

        if (throwable instanceof HttpRetryException) {

            int status = ((HttpRetryException) throwable).responseCode();

            if (status >= 400 && status < 500) {
                throw new NetworkRegisterException();
            } else {
                throw new NetworkGeneralException();
            }

        } else {
            throw new NetworkGeneralException();
        }
    }

    public Completable unregister(String authorization) {
        return loginService.unregister(authorization)
                .doOnSubscribe(disposable -> isRequestingInformation = true)
                .doOnTerminate(() -> isRequestingInformation = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements();
    }

    public Observable<LoginResponse> login(String email, String password) {
        return loginService.login(email, password)
                .doOnSubscribe(disposable -> isRequestingInformation = true)
                .doOnTerminate(() -> isRequestingInformation = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(this::handleLoginError);
    }

    private Observable<LoginResponse> handleLoginError(Throwable throwable) {

        if (throwable instanceof HttpRetryException) {

            int status = ((HttpRetryException) throwable).responseCode();

            if (status >= 400 && status < 500) {
                throw new NetworkLoginException();
            } else {
                throw new NetworkGeneralException();
            }

        } else {
            throw new NetworkGeneralException();
        }
    }

    public boolean isRequestingInformation() {
        return isRequestingInformation;
    }
}
