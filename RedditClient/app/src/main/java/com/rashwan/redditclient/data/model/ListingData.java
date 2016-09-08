package com.rashwan.redditclient.data.model;

import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by rashwan on 9/8/16.
 */

public class ListingData {
    private String after;
    private String before;
    @Json(name = "children") private List<RedditPost> posts;

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }

    public List<RedditPost> getPosts() {
        return posts;
    }
}
