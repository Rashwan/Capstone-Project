package com.rashwan.redditclient.data.model;

import com.squareup.moshi.Json;

/**
 * Created by rashwan on 9/8/16.
 */

public class RedditPost {
    private String kind;
    @Json(name = "data") private RedditPostDataModel redditPostData;

    public String getKind() {
        return kind;
    }

    public RedditPostDataModel getRedditPostData() {
        return redditPostData;
    }
}
