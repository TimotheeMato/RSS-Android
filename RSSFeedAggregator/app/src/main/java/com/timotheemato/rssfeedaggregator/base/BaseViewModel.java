package com.timotheemato.rssfeedaggregator.base;

import com.timotheemato.rssfeedaggregator.network.RequestManager;
import com.timotheemato.rssfeedaggregator.network.models.ErrorResponse;

import retrofit2.adapter.rxjava.HttpException;

/**
 * Created by tmato on 1/27/17.
 */

public class BaseViewModel {
    protected RequestManager requestManager;

    protected void checkError(Throwable e, String message, Lifecycle.View viewCallback) {
        if (e instanceof HttpException) {
            int statusCode = ((HttpException) e).code();
            if (statusCode >= 400 && statusCode < 500) {
                ErrorResponse errorResponse = requestManager.getError(((HttpException) e).response());
                if (errorResponse.getErrors().get(0) != null) {
                    viewCallback.showMessage(errorResponse.getErrors().get(0));
                } else {
                    viewCallback.showMessage(message);
                }
            } else {
                viewCallback.showMessage("Network failure, try again later");
            }
        } else {
            String errorMessage = e.getMessage();
            if (!message.equals("")) {
                viewCallback.showMessage(errorMessage);
            } else {
                viewCallback.showMessage("Technical error, try again later");
            }
        }
    }
}
