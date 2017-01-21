package com.timotheemato.rssfeedaggregator.network.models;

import com.google.gson.annotations.Expose;

/**
 * Created by tmato on 1/21/17.
 */

public class LoginRequest {
    @Expose
    private String email;
    @Expose
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
