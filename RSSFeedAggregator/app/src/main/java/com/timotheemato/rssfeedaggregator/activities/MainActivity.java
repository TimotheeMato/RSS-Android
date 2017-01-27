package com.timotheemato.rssfeedaggregator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.timotheemato.rssfeedaggregator.R;
import com.timotheemato.rssfeedaggregator.data.SharedPrefManager;
import com.timotheemato.rssfeedaggregator.ui.feed.FeedFragment;
import com.timotheemato.rssfeedaggregator.ui.subscriptions.SubscriptionsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by tmato on 1/27/17.
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        sharedPrefManager = SharedPrefManager.getInstance(getApplicationContext());

        if (sharedPrefManager.getToken() == null) {
            logout();
        }

        if (savedInstanceState == null) {
            Fragment initialFragment = SubscriptionsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container, initialFragment).commit();
        }
    }

    private void logout() {
        sharedPrefManager.storeToken(null);
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void showFeed(int feedId, String feedTitle) {
        Fragment fragment = FeedFragment.newInstance(feedId, feedTitle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right);
        ft.replace(R.id.fragment_container, fragment, "feed_fragment");
        ft.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
