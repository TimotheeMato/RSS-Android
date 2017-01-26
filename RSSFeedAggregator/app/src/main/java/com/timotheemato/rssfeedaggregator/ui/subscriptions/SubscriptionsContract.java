package com.timotheemato.rssfeedaggregator.ui.subscriptions;

import com.timotheemato.rssfeedaggregator.base.Lifecycle;

/**
 * Created by tmato on 1/26/17.
 */

public interface SubscriptionsContract {

    interface View extends Lifecycle.View {

        void stopLoading();

        void showMessage(String message);
    }

    interface ViewModel extends Lifecycle.ViewModel {

    }
}
