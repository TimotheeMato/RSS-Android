package com.timotheemato.rssfeedaggregator.ui.subscriptions;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.timotheemato.rssfeedaggregator.R;
import com.timotheemato.rssfeedaggregator.base.BaseFragment;
import com.timotheemato.rssfeedaggregator.base.Lifecycle;
import com.timotheemato.rssfeedaggregator.data.SharedPrefManager;
import com.timotheemato.rssfeedaggregator.network.RequestManager;
import com.timotheemato.rssfeedaggregator.network.models.Subscription;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SubscriptionsFragment extends BaseFragment implements SubscriptionsContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.content_layout)
    LinearLayout contentLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.loading_layout)
    RelativeLayout loadingLayout;
    @BindView(R.id.error_layout)
    RelativeLayout errorLayout;
    private SubscriptionsViewModel subscriptionsViewModel;

    public SubscriptionsFragment() {
        // Required empty public constructor
    }

    public static SubscriptionsFragment newInstance() {
        SubscriptionsFragment fragment = new SubscriptionsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RequestManager requestManager =
                RequestManager.getInstance(getActivity().getApplicationContext());
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getActivity().getApplicationContext());

        subscriptionsViewModel = new SubscriptionsViewModel(requestManager, sharedPrefManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_subscriptions, container, false);

        ButterKnife.bind(this, rootView);

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        subscriptionsViewModel.getSubscriptions();
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {
        return subscriptionsViewModel;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void stopLoading() {
        contentLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
    }

    @Override
    public void startLoading() {
        contentLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError() {
        contentLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public void showContent(List<Subscription> subscriptionList) {
        stopLoading();
        Log.d("subscriptionsFragment", "showContent");
        if (subscriptionList.size() == 0) {
            showError();
        } else {

        }
    }
}
