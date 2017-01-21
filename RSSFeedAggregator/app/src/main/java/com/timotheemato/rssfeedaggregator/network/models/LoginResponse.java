package com.timotheemato.rssfeedaggregator.network.models;

import com.google.gson.annotations.Expose;

/**
 * Created by tmato on 1/21/17.
 */

public class LoginResponse {
    @Expose
    private String token;

    public String getToken() {
        return token;
    }
}
