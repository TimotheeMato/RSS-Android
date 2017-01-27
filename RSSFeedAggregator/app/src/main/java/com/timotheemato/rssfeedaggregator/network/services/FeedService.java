package com.timotheemato.rssfeedaggregator.network.services;

import com.timotheemato.rssfeedaggregator.network.interfaces.IFeedService;
import com.timotheemato.rssfeedaggregator.network.models.Post;
import com.timotheemato.rssfeedaggregator.network.models.SimpleResponse;
import com.timotheemato.rssfeedaggregator.network.models.Subscription;

import java.util.List;

import retrofit2.Retrofit;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by tmato on 1/21/17.
 */

public class FeedService {
    private IFeedService feedService;

    public FeedService(Retrofit retrofit) {
        this.feedService = retrofit.create(IFeedService.class);
    }

    public Observable<List<Post>> getFeed(String authorization, Integer id, Integer limit, Integer offset) {
        return feedService.getFeed(authorization, id, limit, offset)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Subscription>> getSubscriptions(String authorization) {
        return feedService.getSubscriptions(authorization)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<Subscription> subscribe(String authorization, String url) {
        return feedService.subscribe(authorization, url)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<SimpleResponse> unsubscribe(String authorization, Integer id) {
        return feedService.unsubscribe(authorization, id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
