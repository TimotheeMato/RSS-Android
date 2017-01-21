package com.timotheemato.rssfeedaggregator.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by tmato on 1/21/17.
 */

public class SimpleResponse {
    @Expose
    private List<String> errors;

    public List<String> getErrors() {
        return errors;
    }
}
