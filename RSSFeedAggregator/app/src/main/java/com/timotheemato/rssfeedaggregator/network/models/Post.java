package com.timotheemato.rssfeedaggregator.network.models;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created by tmato on 1/21/17.
 */

public class Post {
    @Expose
    private int id;
    @Expose
    private String description;
    @Expose
    private String title;
    @Expose
    private String link;
    @Expose
    private String author;
    @Expose
    private long date;
    @Expose
    private boolean read;
    @Expose
    private boolean favorite;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public boolean isRead() {
        return read;
    }

    public void setRead(boolean read) {
        this.read = read;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}
