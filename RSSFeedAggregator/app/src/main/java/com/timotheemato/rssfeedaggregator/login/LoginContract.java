package com.timotheemato.rssfeedaggregator.login;

import com.timotheemato.rssfeedaggregator.base.Lifecycle;

/**
 * Created by tmato on 1/21/17.
 */

public interface LoginContract {

    interface View extends Lifecycle.View {

        void hideProgressDialog();

        void launchHomeActivity();

        void showMessage(String message);
    }

    interface ViewModel extends Lifecycle.ViewModel {

        void login();
    }
}
