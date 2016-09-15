package com.rashwan.redditclient.data.model;

import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rashwan on 9/8/16.
 */
public class RedditPostDataModel extends ListingKind{
    String author;
    String score;
    public  String domain;
    public  String subreddit;
    public  String name;
    public  String thumbnail;
    public  String title;
    @SerializedName("over_18") public  boolean nsfw;
    @SerializedName("subreddit_id") public  String subredditId;
    @Nullable @SerializedName("post_hint") public  String postHint;
    @SerializedName("url") public  String postUrl;
    @SerializedName("num_comments") public  int numOfComments;

    public String getAuthor() {
        return author;
    }

    public String getScore() {
        return score;
    }


    public String getDomain() {
        return domain;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public String getName() {
        return name;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public boolean isNsfw() {
        return nsfw;
    }

    public String getSubredditId() {
        return subredditId;
    }

    @Nullable
    public String getPostHint() {
        return postHint;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public int getNumOfComments() {
        return numOfComments;
    }
}
