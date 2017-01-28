package com.timotheemato.rssfeedaggregator.network.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Created by tmato on 1/21/17.
 */

public class Post implements Parcelable {
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

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.description);
        dest.writeString(this.title);
        dest.writeString(this.link);
        dest.writeString(this.author);
        dest.writeLong(this.date);
        dest.writeByte((byte) (this.read ? 1 : 0));
        dest.writeByte((byte) (this.favorite ? 1 : 0));
    }

    public Post(Parcel in) {
        this.id = in.readInt();
        this.description = in.readString();
        this.title = in.readString();
        this.link = in.readString();
        this.author = in.readString();
        this.date = in.readLong();
        this.read = in.readByte() != 0;
        this.favorite = in.readByte() != 0;
    }

    public static final Creator<Post> CREATOR = new Creator<Post>() {
        @Override
        public Post createFromParcel(Parcel source) {
            return new Post(source);
        }

        @Override
        public Post[] newArray(int size) {
            return new Post[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }
}
