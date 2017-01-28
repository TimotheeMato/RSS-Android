package com.timotheemato.rssfeedaggregator.ui.detail;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.timotheemato.rssfeedaggregator.R;
import com.timotheemato.rssfeedaggregator.data.SharedPrefManager;
import com.timotheemato.rssfeedaggregator.network.RequestManager;
import com.timotheemato.rssfeedaggregator.network.models.Post;
import com.timotheemato.rssfeedaggregator.network.models.SimpleResponse;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;

public class DetailFragment extends Fragment {

    public static String KEY_POST = "KEY_POST";
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.date_and_author)
    TextView dateAndAuthor;
    @BindView(R.id.description)
    WebView description;
    @BindView(R.id.link_button)
    Button linkButton;

    private Post currentPost;
    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Post post) {
        DetailFragment fragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(KEY_POST, post);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            currentPost = bundle.getParcelable(KEY_POST);
        }
        RequestManager requestManager = RequestManager.getInstance(getActivity().getApplicationContext());
        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance(getActivity().getApplicationContext());
        if (!currentPost.isRead()) {
        requestManager.read(sharedPrefManager.getToken(), currentPost.getId()).subscribe(new Observer<SimpleResponse>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                Log.e("DetailFragment", "Couldn't send the read for " + currentPost.getId());
            }

            @Override
            public void onNext(SimpleResponse simpleResponse) {
                Log.d("DetailFragment", "Is read " + currentPost.getId());
            }
        });
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);

        ButterKnife.bind(this, rootView);

        title.setText(currentPost.getTitle());
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.FRANCE);

        if (currentPost.getAuthor() != null && !currentPost.getAuthor().equals("")) {
            dateAndAuthor.setText("by " + currentPost.getAuthor() + " on " + dateFormat.format(new Date(currentPost.getDate())));
        } else {
            dateAndAuthor.setText("on " + dateFormat.format(new Date(currentPost.getDate())));
        }

        description.getSettings().setJavaScriptEnabled(true);
        description.loadDataWithBaseURL(null, "<style>img{display: inline;height: auto;max-width: 100%;}</style>" + currentPost.getDescription(), "test/html", "utf-8", null);

        return rootView;
    }

    @OnClick(R.id.link_button)
    public void onLinkButtonTap(View view) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(currentPost.getLink()));
        startActivity(browserIntent);
    }
}
