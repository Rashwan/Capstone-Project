package com.rashwan.redditclient.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by rashwan on 9/8/16.
 */
@AutoValue
public abstract class RedditPostDataModel {
    public abstract String domain();
    public abstract String subreddit();
    public abstract String author();
    public abstract String name();
    public abstract String score();
    public abstract String thumbnail();
    public abstract String title();
    @Json(name = "over_18") public abstract boolean nsfw();
    @Json(name = "subreddit_id") public abstract String subredditId();
    @Nullable @Json(name = "post_hint") public abstract String postHint();
    @Json(name = "url") public abstract String postUrl();
    @Json(name = "num_comments") public abstract int numOfComments();

    public static JsonAdapter<RedditPostDataModel> jsonAdapter(Moshi moshi){
        return AutoValue_RedditPostDataModel.jsonAdapter(moshi);
    }
}
