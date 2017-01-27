package com.timotheemato.rssfeedaggregator.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.timotheemato.rssfeedaggregator.R;
import com.timotheemato.rssfeedaggregator.network.models.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tmato on 1/27/17.
 */

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    private List<Post> postList;
    private Context context;

    public static final int VIEW_TYPE_LOADING = 0;
    public static final int VIEW_TYPE_ACTIVITY = 1;

    private boolean isStillLoading = true;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;

        ProgressBar progressBar;
        TextView endOfList;


        public ViewHolder(final View view, boolean isLoading) {
            super(view);
            if (!isLoading) {
                ButterKnife.bind(this, view);
            } else {
                progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
                endOfList = (TextView) view.findViewById(R.id.end_of_list);
            }
        }
    }

    public PostAdapter(Context context) {
        this.context = context;
    }

    public void addPostToList(List<Post> newPosts) {
        if (postList == null) {
            this.postList = newPosts;
        } else {
            this.postList.addAll(newPosts);
        }
        notifyDataSetChanged();
    }

    @Override
    public PostAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        if (viewType == VIEW_TYPE_ACTIVITY) {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.post_row, parent, false);

            final ViewHolder viewHolder = new ViewHolder(view, false);
//        view.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Subscription subscription = subscriptionList.get(viewHolder.getAdapterPosition());
//                ((MainActivity)context).showFeed(subscription.getId(), subscription.getTitle());
//            }
//        });

            return viewHolder;
        } else {
            View view = LayoutInflater.from(context)
                    .inflate(R.layout.progressbar_row, parent, false);

            final ViewHolder viewHolder = new ViewHolder(view, true);

            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (position < postList.size()) {
            Post currentPost = postList.get(position);
            holder.title.setText(currentPost.getTitle());
        } else {
            if (!isStillLoading) {
                holder.progressBar.setVisibility(View.GONE);
                holder.endOfList.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return postList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return (position >= postList.size()) ? VIEW_TYPE_LOADING
                : VIEW_TYPE_ACTIVITY;
    }

    public void stopEndless() {
        isStillLoading = false;
        notifyDataSetChanged();
    }

    public boolean isStillLoading() {
        return isStillLoading;
    }
}