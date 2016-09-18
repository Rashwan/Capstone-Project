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
    @Nullable public abstract String domain();
    public abstract String subreddit();
    @Nullable public abstract String name();
    public abstract String thumbnail();
    public abstract String title();
    @SerializedName("over_18") public abstract boolean nsfw();
    @Nullable @SerializedName("subreddit_id") public abstract String subredditId();
    @Nullable @SerializedName("post_hint") public abstract String postHint();
    @Nullable @SerializedName("url") public abstract String postUrl();
    @SerializedName("num_comments") public abstract int numOfComments();
    private int id;
    public static TypeAdapter<RedditPostDataModel> typeAdapter(Gson gson) {
        return new AutoValue_RedditPostDataModel.GsonTypeAdapter(gson);
    }

    public static RedditPostDataModel create(String author,String score,String subreddit
            ,String thumbnail,String title,int numOfComments){
        return new AutoValue_RedditPostDataModel(author,score,"w",subreddit,"e",thumbnail
        ,title,false,"f","g","h",numOfComments);
    }

    @Override
    public String getType() {
        return RedditPostDataModel.class.getSimpleName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
