package com.timotheemato.rssfeedaggregator.models;

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
