package com.timotheemato.rssfeedaggregator.base;

import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableMaybeObserver;
import io.reactivex.observers.DisposableObserver;

import static com.timotheemato.rssfeedaggregator.base.Constants.REQUEST_FAILED;
import static com.timotheemato.rssfeedaggregator.base.Constants.REQUEST_NONE;
import static com.timotheemato.rssfeedaggregator.base.Constants.REQUEST_RUNNING;
import static com.timotheemato.rssfeedaggregator.base.Constants.REQUEST_SUCCEEDED;

/**
 * Created by tmato on 1/22/17.
 */

public abstract class NetworkViewModel {

    protected @Constants.RequestState
    int requestState;
    protected Throwable lastError;

    public abstract boolean isRequestingInformation();

    public NetworkViewModel() {

        this.requestState = REQUEST_NONE;
    }

    public @Constants.RequestState
    int getRequestState() {

        if (isRequestingInformation()) {
            return REQUEST_RUNNING;
        }

        return requestState;
    }

    public Throwable getLastError() {

        return lastError;
    }

    protected class MaybeNetworkObserver<T> extends DisposableMaybeObserver<T> {

        @Override
        public void onSuccess(T value) {

            requestState = REQUEST_SUCCEEDED;
        }

        @Override
        public void onError(Throwable e) {

            lastError = e;
            requestState = REQUEST_FAILED;
        }

        @Override
        public void onComplete() {

        }
    }
}
