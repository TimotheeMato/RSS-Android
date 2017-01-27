package com.timotheemato.rssfeedaggregator.ui.feed;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.timotheemato.rssfeedaggregator.R;
import com.timotheemato.rssfeedaggregator.base.BaseFragment;
import com.timotheemato.rssfeedaggregator.base.Lifecycle;
import com.timotheemato.rssfeedaggregator.data.SharedPrefManager;
import com.timotheemato.rssfeedaggregator.network.RequestManager;
import com.timotheemato.rssfeedaggregator.network.models.Post;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedFragment extends BaseFragment implements FeedContract.View {

    private static String KEY_ID = "KEY_ID";
    private static String KEY_TITLE = "KEY_TITLE";

    @BindView(R.id.feed_title)
    TextView feedTitleTextView;
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.content_layout)
    RelativeLayout contentLayout;
    @BindView(R.id.loading_layout)
    RelativeLayout loadingLayout;
    @BindView(R.id.error_layout)
    RelativeLayout errorLayout;

    private FeedViewModel feedViewModel;
    private int feedId;
    private String feedTitle;

    public FeedFragment() {
        // Required empty public constructor
    }

    public static FeedFragment newInstance(int id, String title) {
        FeedFragment fragment = new FeedFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_ID, id);
        bundle.putString(KEY_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            feedId = bundle.getInt(KEY_ID);
            feedTitle = bundle.getString(KEY_TITLE);
        }

        RequestManager requestManager =
                RequestManager.getInstance(getActivity().getApplicationContext());
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getActivity().getApplicationContext());

        feedViewModel = new FeedViewModel(requestManager, sharedPrefManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_feed, container, false);

        ButterKnife.bind(this, rootView);

        feedTitleTextView.setText(feedTitle);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        adapter = new FeedAdapter(getContext());
//        recyclerView.setAdapter(adapter);

        return rootView;
    }

    @Override
    protected Lifecycle.ViewModel getViewModel() {
        return feedViewModel;
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
    public void showContent(List<Post> postList) {
        stopLoading();
        if (postList.size() == 0) {
            showError();
        } else {
            //adapter.getPostList(subscriptionList);
            contentLayout.setVisibility(View.VISIBLE);
        }
    }
}
