package com.timotheemato.rssfeedaggregator.ui.login;

/**
 * Created by tmato on 1/21/17.
 */

public interface LoginContract {

    interface View {

        void stopLoading();

        void launchHomeActivity();

        void showMessage(String message);
    }

    interface ViewModel {

        void login(String email, String password);
        void register(String email, String password);
    }
}
