package com.timotheemato.rssfeedaggregator.ui.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.timotheemato.rssfeedaggregator.base.Constants;
import com.timotheemato.rssfeedaggregator.base.Lifecycle;
import com.timotheemato.rssfeedaggregator.base.NetworkViewModel;
import com.timotheemato.rssfeedaggregator.network.RequestManager;
import com.timotheemato.rssfeedaggregator.network.models.ErrorResponse;
import com.timotheemato.rssfeedaggregator.network.models.LoginResponse;

import static com.timotheemato.rssfeedaggregator.base.Constants.REQUEST_FAILED;
import static com.timotheemato.rssfeedaggregator.base.Constants.REQUEST_SUCCEEDED;

/**
 * Created by tmato on 1/22/17.
 */

public class LoginViewModel extends NetworkViewModel implements LoginContract.ViewModel {

    private LoginContract.View viewCallback;
    private RequestManager requestManager;

    public LoginViewModel(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    @Override
    public void onViewResumed() {

        @Constants.RequestState int requestState = getRequestState();
        if (requestState == REQUEST_SUCCEEDED) {
            onLoginCompleted();
        } else if (requestState == REQUEST_FAILED) {
            onLoginError(getLastError());
        }
    }

    @Override
    public void onViewAttached(@NonNull Lifecycle.View viewCallback) {
        this.viewCallback = (LoginContract.View) viewCallback;
    }

    @Override
    public void onViewDetached() {
        this.viewCallback = null;
    }

    @Override
    public boolean isRequestingInformation() {
        return requestManager.isRequestingInformation();
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

    private void onLoginError(Throwable e) {
        if (viewCallback != null) {

            viewCallback.stopLoading();

            if (e instanceof HttpException) {
                int statusCode = ((HttpException) e).code();
                if (statusCode >= 400 && statusCode < 500) {
                    ErrorResponse errorResponse = requestManager.getError(((HttpException) e).response());
                    if (errorResponse.getErrors().get(0) != null) {
                        viewCallback.showMessage(errorResponse.getErrors().get(0));
                    } else {
                        viewCallback.showMessage("Login failure");
                    }
                } else {
                    viewCallback.showMessage("Network failure, try again later");
                }
            } else {
                String message = e.getMessage();
                if (!message.equals("")) {
                    viewCallback.showMessage(message);
                } else {
                    viewCallback.showMessage("Technical error, try again later");
                }
            }
        }
    }

    private void onRegisterCompleted() {
        if (viewCallback != null) {

            viewCallback.stopLoading();
            viewCallback.showMessage("Registration complete, you can now login");
        }
    }

    private void onRegisterError(Throwable e) {
        if (e instanceof NullPointerException) {
            onRegisterCompleted();
            return;
        }
        if (viewCallback != null) {

            viewCallback.stopLoading();
            if (e instanceof HttpException) {
                int statusCode = ((HttpException) e).code();
                if (statusCode >= 400 && statusCode < 500) {
                    ErrorResponse errorResponse = requestManager.getError(((HttpException) e).response());
                    if (errorResponse.getErrors().get(0) != null) {
                        viewCallback.showMessage(errorResponse.getErrors().get(0));
                    } else {
                        viewCallback.showMessage("Register failure");
                    }
                } else {
                    viewCallback.showMessage("Network failure, try again later");
                }
            } else {
                String message = e.getMessage();
                if (!message.equals("")) {
                    viewCallback.showMessage(message);
                } else {
                    viewCallback.showMessage("Technical error, try again later");
                }
            }
        }
    }

    private class LoginObserver extends MaybeNetworkObserver<LoginResponse> {

        @Override
        public void onSuccess(LoginResponse response) {
            onLoginCompleted();
        }

        @Override
        public void onError(Throwable e) {
            onLoginError(e);
        }

        @Override
        public void onComplete() {

        }
    }

    private class RegisterObserver extends MaybeNetworkObserver<Void> {

        @Override
        public void onSuccess(Void response) {
            onRegisterCompleted();
        }

        @Override
        public void onError(Throwable e) {
            onRegisterError(e);
        }

        @Override
        public void onComplete() {

        }
    }
}
