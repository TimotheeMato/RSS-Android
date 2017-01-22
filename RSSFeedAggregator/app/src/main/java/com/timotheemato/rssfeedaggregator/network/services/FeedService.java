package com.timotheemato.rssfeedaggregator.network.services;

import com.timotheemato.rssfeedaggregator.network.interfaces.IFeedService;
import com.timotheemato.rssfeedaggregator.network.models.Feed;
import com.timotheemato.rssfeedaggregator.network.models.Subscription;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

/**
 * Created by tmato on 1/21/17.
 */

public class FeedService {
    private IFeedService feedService;
    private boolean isRequestingInformation = false;

    public FeedService(Retrofit retrofit) {
        this.feedService = retrofit.create(IFeedService.class);
    }

    public Observable<Feed> getFeed(Integer limit, Integer offset) {
        return feedService.getFeed(limit, offset)
                .doOnSubscribe(disposable -> isRequestingInformation = true)
                .doOnTerminate(() -> isRequestingInformation = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Observable<List<Subscription>> getSubscriptions(String authorization) {
        return feedService.getSubscriptions(authorization)
                .doOnSubscribe(disposable -> isRequestingInformation = true)
                .doOnTerminate(() -> isRequestingInformation = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Completable subscribe(String authorization, String url) {
        return feedService.subscribe(authorization, url)
                .doOnSubscribe(disposable -> isRequestingInformation = true)
                .doOnTerminate(() -> isRequestingInformation = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements();
    }

    public Completable unsubscribe(String authorization, Integer id) {
        return feedService.unsubscribe(authorization, id)
                .doOnSubscribe(disposable -> isRequestingInformation = true)
                .doOnTerminate(() -> isRequestingInformation = false)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .ignoreElements();
    }

    public boolean isRequestingInformation() {
        return isRequestingInformation;
    }
}
