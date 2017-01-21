package com.timotheemato.rssfeedaggregator.base;

import android.support.annotation.NonNull;

/**
 * Created by tmato on 1/21/17.
 */

public interface Lifecycle {

    interface View {

    }

    interface ViewModel {

        void onViewResumed();
        void onViewAttached(@NonNull Lifecycle.View viewCallback);
        void onViewDetached();
    }
}