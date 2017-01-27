package com.timotheemato.rssfeedaggregator.data;

import android.content.Context;
import android.content.SharedPreferences;

import com.timotheemato.rssfeedaggregator.R;

/**
 * Created by tmato on 1/21/17.
 */

public class SharedPrefManager {
    public static final String TOKEN_KEY = "token";

    private static SharedPrefManager instance;

    private SharedPreferences sharedPreferences;

    private SharedPrefManager(Context context) {
        this.sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.share_pref), Context.MODE_PRIVATE);
    }

    public static SharedPrefManager getInstance(Context context) {

        synchronized (SharedPrefManager.class) {
            if (instance == null) {
                instance = new SharedPrefManager(context);
            }
            return instance;
        }
    }

    public String getToken() {
        return sharedPreferences.getString(TOKEN_KEY, null);
    }

    public void storeToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(TOKEN_KEY, token);
        editor.apply();
    }
}
