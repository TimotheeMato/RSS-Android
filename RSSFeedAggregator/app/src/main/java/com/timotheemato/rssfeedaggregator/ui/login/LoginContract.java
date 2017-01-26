package com.timotheemato.rssfeedaggregator.ui.login;

import com.timotheemato.rssfeedaggregator.base.Lifecycle;

/**
 * Created by tmato on 1/21/17.
 */

public interface LoginContract {

    interface View extends Lifecycle.View {

        void stopLoading();

        void launchHomeActivity();

        void showMessage(String message);
    }

    interface ViewModel extends Lifecycle.ViewModel {

        void login(String email, String password);
        void register(String email, String password);
    }
}
