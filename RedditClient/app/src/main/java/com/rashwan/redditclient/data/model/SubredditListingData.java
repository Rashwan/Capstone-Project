package com.rashwan.redditclient.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rashwan on 9/12/16.
 */

public class SubredditListingData {
    private String after;
    private String before;
    @SerializedName("children") private List<SubredditDetailsResponse> subreddits;

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }

    public List<SubredditDetailsResponse> getSubreddits() {
        return subreddits;
    }
}
