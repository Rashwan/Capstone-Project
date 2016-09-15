package com.rashwan.redditclient.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rashwan on 9/8/16.
 */
@AutoValue
public abstract class RedditPostDataModel extends ListingKind{
    public abstract String author();
    public abstract String score();
    public abstract String domain();
    public abstract String subreddit();
    public abstract String name();
    public abstract String id();
    public abstract String thumbnail();
    public abstract String title();
    @SerializedName("selftext") public abstract String body();
    @SerializedName("over_18") public abstract boolean nsfw();
    @SerializedName("subreddit_id") public abstract String subredditId();
    @Nullable @SerializedName("post_hint") public abstract String postHint();
    @SerializedName("url") public abstract String postUrl();
    @SerializedName("num_comments") public abstract int numOfComments();

    public static TypeAdapter<RedditPostDataModel> typeAdapter(Gson gson) {
        return new AutoValue_RedditPostDataModel.GsonTypeAdapter(gson);
    }

    @Override
    public String getType() {
        return RedditPostDataModel.class.getSimpleName();
    }
}
