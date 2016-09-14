package com.rashwan.redditclient.data.model;

/**
 * Created by rashwan on 9/14/16.
 */

public class ListingKind {
    String author;
    String score;
    String type;

    public String getAuthor() {
        return author;
    }

    public String getScore() {
        return score;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
