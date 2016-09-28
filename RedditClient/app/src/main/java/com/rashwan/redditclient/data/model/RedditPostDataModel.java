package com.rashwan.redditclient.data.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rashwan on 9/8/16.
 */
@AutoValue
public abstract class RedditPostDataModel extends ListingKind implements Parcelable{
    public abstract String author();
    public abstract String score();
    public abstract String subreddit();
    public abstract String title();
    @Nullable public abstract String id();
    @Nullable public abstract String domain();
    @Nullable public abstract String name();
    @Nullable public abstract String thumbnail();
    @Nullable @SerializedName("selftext") public abstract String body();
    @Nullable @SerializedName("subreddit_id") public abstract String subredditId();
    @Nullable @SerializedName("post_hint") public abstract String postHint();
    @Nullable @SerializedName("url") public abstract String postUrl();
    @SerializedName("over_18") public abstract boolean nsfw();
    @SerializedName("num_comments") public abstract int numOfComments();
    @SerializedName("is_self") public abstract boolean isSelf();
    private int id;
    public static TypeAdapter<RedditPostDataModel> typeAdapter(Gson gson) {
        return new AutoValue_RedditPostDataModel.GsonTypeAdapter(gson);
    }

    public static RedditPostDataModel create(String postId,String author,String score,String subreddit
            ,String thumbnail,String title,int numOfComments){
        return new AutoValue_RedditPostDataModel(author,score,subreddit,title,postId,null,null
        ,thumbnail,null,null,null,null,false,numOfComments,false);
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
