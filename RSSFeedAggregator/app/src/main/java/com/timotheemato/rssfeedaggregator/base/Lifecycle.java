package com.timotheemato.rssfeedaggregator.base;

import android.support.annotation.NonNull;

/**
 * Created by tmato on 1/26/17.
 */

public interface Lifecycle {

    interface View {
        void showMessage(String message);
    }

    interface ViewModel {

        void onViewAttached(@NonNull Lifecycle.View viewCallback);
        void onViewDetached();
    }
}
