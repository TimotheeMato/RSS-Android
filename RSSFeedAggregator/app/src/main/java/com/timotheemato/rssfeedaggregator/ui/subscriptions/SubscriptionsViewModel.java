package com.timotheemato.rssfeedaggregator.ui.subscriptions;

import android.support.annotation.NonNull;
import android.util.Log;

import com.timotheemato.rssfeedaggregator.base.BaseViewModel;
import com.timotheemato.rssfeedaggregator.base.Lifecycle;
import com.timotheemato.rssfeedaggregator.data.SharedPrefManager;
import com.timotheemato.rssfeedaggregator.network.RequestManager;
import com.timotheemato.rssfeedaggregator.network.models.Subscription;

import java.util.List;

import rx.Observer;

/**
 * Created by tmato on 1/26/17.
 */

public class SubscriptionsViewModel extends BaseViewModel implements SubscriptionsContract.ViewModel {

    private SubscriptionsContract.View viewCallback;
    private SharedPrefManager sharedPrefManager;

    public SubscriptionsViewModel(RequestManager requestManager, SharedPrefManager sharedPrefManager) {
        this.requestManager = requestManager;
        this.sharedPrefManager = sharedPrefManager;
    }

    @Override
    public void onViewAttached(@NonNull Lifecycle.View viewCallback) {
        this.viewCallback = (SubscriptionsContract.View) viewCallback;
    }

    @Override
    public void onViewDetached() {
        this.viewCallback = null;
    }

    @Override
    public void getSubscriptions() {
        String token = sharedPrefManager.getToken();
        requestManager.getSubscriptions(token).subscribe(new SubscriptionsObserver());
    }

    private void onGetSubscriptionsError(Throwable e) {
        if (viewCallback != null) {
            viewCallback.stopLoading();
            checkError(e, "Couldn't get subscriptions", viewCallback);
        }
    }

    private void onGetSubscriptionsSuccess(List<Subscription> subscriptionList) {
        if (viewCallback != null) {
            viewCallback.stopLoading();
            viewCallback.showContent(subscriptionList);
        }
    }

    private void onSubscriptionError(Throwable e) {
        if (viewCallback != null) {
            viewCallback.stopLoading();
            checkError(e, "Couldn't subscribe", viewCallback);
            viewCallback.showError();
        }
    }

    private void onSubscriptionSuccess(Subscription subscription) {
        if (viewCallback != null) {
            viewCallback.showMessage("You have been subscribed");
            getSubscriptions();
        }
    }

    @Override
    public void subscribe(String url) {
        String token = sharedPrefManager.getToken();
        requestManager.subscribe(token, url).subscribe(new SubscriptionObserver());
    }

    private class SubscriptionsObserver implements Observer<List<Subscription>> {

        @Override
        public void onNext(List<Subscription> response) {
            onGetSubscriptionsSuccess(response);
        }

        @Override
        public void onError(Throwable e) {
            onGetSubscriptionsError(e);
        }

        @Override
        public void onCompleted() {

        }
    }

    private class SubscriptionObserver implements Observer<Subscription> {

        @Override
        public void onNext(Subscription response) {
            onSubscriptionSuccess(response);
        }

        @Override
        public void onError(Throwable e) {
            onSubscriptionError(e);
        }

        @Override
        public void onCompleted() {

        }
    }
}
