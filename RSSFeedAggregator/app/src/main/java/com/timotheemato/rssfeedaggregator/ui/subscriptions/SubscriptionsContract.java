package com.timotheemato.rssfeedaggregator.ui.subscriptions;

import com.timotheemato.rssfeedaggregator.base.Lifecycle;
import com.timotheemato.rssfeedaggregator.network.models.Subscription;

import java.util.List;

/**
 * Created by tmato on 1/26/17.
 */

public interface SubscriptionsContract {

    interface View extends Lifecycle.View {
        void stopLoading();
        void showError();
        void showContent(List<Subscription> subscriptionList);
    }

    interface ViewModel extends Lifecycle.ViewModel {
        void getSubscriptions();
        void subscribe(String url);
    }
}
