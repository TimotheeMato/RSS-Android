package com.timotheemato.rssfeedaggregator.ui.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timotheemato.rssfeedaggregator.R;
import com.timotheemato.rssfeedaggregator.network.models.Subscription;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tmato on 1/27/17.
 */

public class SubscriptionsAdapter extends RecyclerView.Adapter<SubscriptionsAdapter.ViewHolder> {
    private List<Subscription> subscriptionList;
    private Context context;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public SubscriptionsAdapter(Context context) {
        this.context = context;
    }

    public void setSubscriptionList(List<Subscription> subscriptionList) {
        this.subscriptionList = subscriptionList;
        notifyDataSetChanged();
    }

    @Override
    public SubscriptionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View v = LayoutInflater.from(context)
                .inflate(R.layout.subscription_row, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
            Subscription currentSubscription = subscriptionList.get(position);
            holder.title.setText(currentSubscription.getTitle());
            holder.description.setText(currentSubscription.getDescription());
    }

    @Override
    public int getItemCount() {
        return subscriptionList.size();
    }
}

