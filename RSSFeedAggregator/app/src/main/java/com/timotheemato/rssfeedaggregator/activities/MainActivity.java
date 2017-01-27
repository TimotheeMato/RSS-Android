package com.timotheemato.rssfeedaggregator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.timotheemato.rssfeedaggregator.R;
import com.timotheemato.rssfeedaggregator.data.SharedPrefManager;
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
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }

        Fragment initialFragment = SubscriptionsFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, initialFragment).commit();
    }
}
