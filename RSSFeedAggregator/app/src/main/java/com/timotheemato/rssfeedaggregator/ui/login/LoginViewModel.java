package com.timotheemato.rssfeedaggregator.ui.login;

import android.support.annotation.NonNull;

import com.timotheemato.rssfeedaggregator.network.RequestManager;
import com.timotheemato.rssfeedaggregator.network.models.ErrorResponse;
import com.timotheemato.rssfeedaggregator.network.models.LoginResponse;
import com.timotheemato.rssfeedaggregator.network.models.SimpleResponse;

import retrofit2.adapter.rxjava.HttpException;
import rx.Observer;

/**
 * Created by tmato on 1/22/17.
 */

public class LoginViewModel implements LoginContract.ViewModel {

    private LoginContract.View viewCallback;
    private RequestManager requestManager;

    private String token;

    public LoginViewModel(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    public void login(String email, String password) {
        requestManager.login(email, password).subscribe(new LoginObserver());
    }

    public void register(String email, String password) {
        requestManager.register(email, password).subscribe(new RegisterObserver());
    }

    private void onLoginCompleted() {
        if (viewCallback != null) {

            viewCallback.stopLoading();
            viewCallback.launchHomeActivity();
        }
    }

    private void checkError(Throwable e, String message) {
        if (e instanceof HttpException) {
            int statusCode = ((HttpException) e).code();
            if (statusCode >= 400 && statusCode < 500) {
                ErrorResponse errorResponse = requestManager.getError(((HttpException) e).response());
                if (errorResponse.getErrors().get(0) != null) {
                    viewCallback.showMessage(errorResponse.getErrors().get(0));
                } else {
                    viewCallback.showMessage(message);
                }
            } else {
                viewCallback.showMessage("Network failure, try again later");
            }
        } else {
            String errorMessage = e.getMessage();
            if (!message.equals("")) {
                viewCallback.showMessage(errorMessage);
            } else {
                viewCallback.showMessage("Technical error, try again later");
            }
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
            onLoginCompleted();
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
