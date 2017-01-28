package com.timotheemato.rssfeedaggregator.ui.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.timotheemato.rssfeedaggregator.R;
import com.timotheemato.rssfeedaggregator.activities.MainActivity;
import com.timotheemato.rssfeedaggregator.data.SharedPrefManager;
import com.timotheemato.rssfeedaggregator.network.RequestManager;
import com.timotheemato.rssfeedaggregator.network.models.SimpleResponse;
import com.timotheemato.rssfeedaggregator.network.models.Subscription;
import com.timotheemato.rssfeedaggregator.ui.subscriptions.SubscriptionsFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observer;

/**
 * Created by tmato on 1/27/17.
 */

public class SubscriptionsAdapter extends RecyclerView.Adapter<SubscriptionsAdapter.ViewHolder> {
    private List<Subscription> subscriptionList;
    private Context context;
    private SubscriptionsFragment fragment;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;

        public ViewHolder(final View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public SubscriptionsAdapter(Context context, SubscriptionsFragment fragment) {
        this.context = context;
        this.fragment = fragment;
    }

    public void setSubscriptionList(List<Subscription> subscriptionList) {
        this.subscriptionList = subscriptionList;
        notifyDataSetChanged();
    }

    @Override
    public SubscriptionsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                              int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.subscription_row, parent, false);

        final ViewHolder viewHolder = new ViewHolder(view);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Subscription subscription = subscriptionList.get(viewHolder.getAdapterPosition());
                ((MainActivity)context).showFeed(subscription.getId(), subscription.getTitle());
            }
        });
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Subscription subscription = subscriptionList.get(viewHolder.getAdapterPosition());
                createUnsubscriptionDialog(subscription.getId());
                return true;
            }
        });

        return viewHolder;
    }

    private void createUnsubscriptionDialog(final int feedId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Unsunscribe")
                .setMessage("Do you want to unsubscribe from this feed?")
                .setPositiveButton("Unsubscribe", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(context);
                        RequestManager requestManager = RequestManager.getInstance(context);
                        requestManager.unsubscribe(sharedPrefManager.getToken(), feedId).subscribe(new Observer<SimpleResponse>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("Subscription", e.getLocalizedMessage());
                            }

                            @Override
                            public void onNext(SimpleResponse simpleResponse) {
                                Log.d("Subscription", "Unsubscribed from " + feedId);
                                fragment.onStart();
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog alert = builder.create();

        alert.show();

        Button nbutton = alert.getButton(DialogInterface.BUTTON_NEGATIVE);
        nbutton.setTextColor(context.getResources().getColor(R.color.colorPrimary));
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(context.getColor(R.color.colorPrimary));
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

