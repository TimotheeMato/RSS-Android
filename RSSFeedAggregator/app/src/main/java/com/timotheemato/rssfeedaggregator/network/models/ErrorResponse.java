package com.timotheemato.rssfeedaggregator.network.models;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by tmato on 1/26/17.
 */

public class ErrorResponse {
    @Expose
    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
