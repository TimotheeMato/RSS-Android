package com.timotheemato.rssfeedaggregator.ui.subscriptions;

import android.support.annotation.NonNull;

import com.timotheemato.rssfeedaggregator.base.Lifecycle;
import com.timotheemato.rssfeedaggregator.data.SharedPrefManager;
import com.timotheemato.rssfeedaggregator.network.RequestManager;

/**
 * Created by tmato on 1/26/17.
 */

public class SubscriptionsViewModel implements Lifecycle.ViewModel {

    private SubscriptionsContract.View viewCallback;
    private RequestManager requestManager;
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
}
