package com.timotheemato.rssfeedaggregator.ui.subscriptions;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
import butterknife.OnClick;

public class SubscriptionsFragment extends BaseFragment implements SubscriptionsContract.View {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.content_layout)
    RelativeLayout contentLayout;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.loading_layout)
    RelativeLayout loadingLayout;
    @BindView(R.id.error_layout)
    RelativeLayout errorLayout;
    @BindView(R.id.add_subscription_button)
    FloatingActionButton addSubscriptionButton;
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
        startLoading();
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
        addSubscriptionButton.setVisibility(View.VISIBLE);
        contentLayout.setVisibility(View.GONE);
        errorLayout.setVisibility(View.GONE);
        loadingLayout.setVisibility(View.GONE);
    }

    public void startLoading() {
        addSubscriptionButton.setVisibility(View.GONE);
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
        Log.d("subscriptionsFragment", "subscriptionList size : " + subscriptionList.size());
        if (subscriptionList.size() == 0) {
            showError();
        } else {

        }
    }

    private void createSubscriptionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getContext(), R.style.DialogCustom));
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setTitle("Enter an URL");

        View alertView = inflater.inflate(R.layout.dialog_subscribe, null);
        builder.setView(alertView)
                // Add action buttons
                .setPositiveButton("Subscribe", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        EditText editText = (EditText) alertView.findViewById(R.id.url);
                        String url = editText.getText().toString();
                        if (url.equals("")) {
                            showMessage("You need to enter an URL");
                        } else {
                            subscriptionsViewModel.subscribe(url);
                        }
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
        nbutton.setTextColor(getResources().getColor(R.color.colorPrimary));
        Button pbutton = alert.getButton(DialogInterface.BUTTON_POSITIVE);
        pbutton.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    @OnClick(R.id.add_subscription_button)
    public void addSubscription(View view) {
        createSubscriptionDialog();
    }
}
