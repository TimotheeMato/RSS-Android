package com.timotheemato.rssfeedaggregator.ui.feed;

import android.support.annotation.NonNull;

import com.timotheemato.rssfeedaggregator.base.BaseViewModel;
import com.timotheemato.rssfeedaggregator.base.Lifecycle;
import com.timotheemato.rssfeedaggregator.data.SharedPrefManager;
import com.timotheemato.rssfeedaggregator.network.RequestManager;
import com.timotheemato.rssfeedaggregator.network.models.Post;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import rx.Observer;

/**
 * Created by tmato on 1/27/17.
 */

public class FeedViewModel extends BaseViewModel implements FeedContract.ViewModel {
    private FeedContract.View viewCallback;
    private SharedPrefManager sharedPrefManager;

    public FeedViewModel(RequestManager requestManager, SharedPrefManager sharedPrefManager) {
        this.requestManager = requestManager;
        this.sharedPrefManager = sharedPrefManager;
    }

    @Override
    public void onViewAttached(@NonNull Lifecycle.View viewCallback) {
        this.viewCallback = (FeedContract.View) viewCallback;
    }

    @Override
    public void onViewDetached() {
        this.viewCallback = null;
    }

    @Override
    public void getPosts(int feedId, int limit, int offset) {
        String token = sharedPrefManager.getToken();
        requestManager.getFeed(token, feedId, limit, offset).subscribe(new FeedObserver());
    }

    private void onFeedError(Throwable e) {
        if (viewCallback != null) {
            viewCallback.stopLoading();
            checkError(e, "Couldn't get posts", viewCallback);
        }
    }

    private void onFeedSuccess(List<Post> postList) {
        if (viewCallback != null) {
            viewCallback.showContent(postList);
        }
    }

    private class FeedObserver implements Observer<List<Post>> {

        @Override
        public void onNext(List<Post> response) {
            onFeedSuccess(response);
        }

        @Override
        public void onError(Throwable e) {
            onFeedError(e);
        }

        @Override
        public void onCompleted() {

        }
    }
}
