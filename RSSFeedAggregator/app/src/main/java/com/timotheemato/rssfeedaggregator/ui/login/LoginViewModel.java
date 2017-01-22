package com.timotheemato.rssfeedaggregator.ui.login;

import android.support.annotation.NonNull;

import com.timotheemato.rssfeedaggregator.R;
import com.timotheemato.rssfeedaggregator.base.Constants;
import com.timotheemato.rssfeedaggregator.base.Lifecycle;
import com.timotheemato.rssfeedaggregator.base.NetworkViewModel;
import com.timotheemato.rssfeedaggregator.network.RequestManager;
import com.timotheemato.rssfeedaggregator.network.exceptions.NetworkLoginException;
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

    public void login() {
        // TODO : Request login
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

            if (e instanceof NetworkLoginException) {

                viewCallback.showMessage(R.string.login_error);

            } else {

                viewCallback.showMessage(R.string.network_error);
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
}
