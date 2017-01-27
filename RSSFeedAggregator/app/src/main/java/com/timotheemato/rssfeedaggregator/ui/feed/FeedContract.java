package com.timotheemato.rssfeedaggregator.ui.feed;

import com.timotheemato.rssfeedaggregator.base.Lifecycle;
import com.timotheemato.rssfeedaggregator.network.models.Post;

import java.util.List;

/**
 * Created by tmato on 1/27/17.
 */

public interface FeedContract {
    interface View extends Lifecycle.View {
        void stopLoading();
        void showError();
        void showContent(List<Post> postList);
    }

    interface ViewModel extends Lifecycle.ViewModel {
        void getPosts(int feedId, int limit, int offset);
    }
}
