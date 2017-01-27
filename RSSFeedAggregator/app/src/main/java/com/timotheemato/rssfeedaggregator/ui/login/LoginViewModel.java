package com.timotheemato.rssfeedaggregator.ui.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.timotheemato.rssfeedaggregator.base.BaseViewModel;
import com.timotheemato.rssfeedaggregator.base.Lifecycle;
import com.timotheemato.rssfeedaggregator.data.SharedPrefManager;
import com.timotheemato.rssfeedaggregator.network.RequestManager;
import com.timotheemato.rssfeedaggregator.network.models.ErrorResponse;
import com.timotheemato.rssfeedaggregator.network.models.LoginResponse;
import com.timotheemato.rssfeedaggregator.network.models.SimpleResponse;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

/**
 * Created by tmato on 1/22/17.
 */

public class LoginViewModel extends BaseViewModel implements LoginContract.ViewModel {

    private LoginContract.View viewCallback;
    private SharedPrefManager sharedPrefManager;

    public LoginViewModel(RequestManager requestManager, SharedPrefManager sharedPrefManager) {
        this.requestManager = requestManager;
        this.sharedPrefManager = sharedPrefManager;
    }

    @Override
    public void onViewAttached(@NonNull Lifecycle.View viewCallback) {
        this.viewCallback = (LoginContract.View) viewCallback;
    }

    @Override
    public void onViewDetached() {
        this.viewCallback = null;
    }

    public void login(String email, String password) {
        requestManager.login(email, password).subscribe(new LoginObserver());
    }

    public void register(String email, String password) {
        requestManager.register(email, password).subscribe(new RegisterObserver());
    }

    private void onLoginCompleted(LoginResponse response) {
        if (viewCallback != null) {
            sharedPrefManager.storeToken(response.getToken());
            viewCallback.stopLoading();
            viewCallback.launchHomeActivity();
        }
    }

    private void onLoginError(Throwable e) {
        if (viewCallback != null) {
            viewCallback.stopLoading();
            checkError(e, "Login failure");
        }
    }

    private void onRegisterCompleted() {
        if (viewCallback != null) {

            viewCallback.stopLoading();
            viewCallback.showMessage("Registration complete, you can now login");
        }
    }

    private void onRegisterError(Throwable e) {
        if (viewCallback != null) {

            viewCallback.stopLoading();
            checkError(e, "Registration failure");
        }
    }

    private class LoginObserver implements Observer<LoginResponse> {

        @Override
        public void onNext(LoginResponse response) {
            onLoginCompleted(response);
        }

        @Override
        public void onError(Throwable e) {
            onLoginError(e);
        }

        @Override
        public void onCompleted() {

        }
    }

    private class RegisterObserver implements Observer<SimpleResponse> {

        @Override
        public void onNext(SimpleResponse response) {
            onRegisterCompleted();
        }

        @Override
        public void onError(Throwable e) {
            onRegisterError(e);
        }

        @Override
        public void onCompleted() {

        }
    }
}
