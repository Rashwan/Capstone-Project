package com.rashwan.redditclient.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rashwan on 9/14/16.
 */
@AutoValue
public abstract class RedditCommentDataModel extends ListingKind{

    public abstract String author();
    public abstract String score();
    public abstract String body();
    private transient ListingResponse replies;
    @SerializedName("parent_id") public abstract String parentId();


    public static TypeAdapter<RedditCommentDataModel> typeAdapter(Gson gson) {
        return new AutoValue_RedditCommentDataModel.GsonTypeAdapter(gson);
    }

    @Override
    public String getType() {
        return RedditCommentDataModel.class.getSimpleName();
    }

    public ListingResponse getReplies() {
        return replies;
    }

    public void setReplies(ListingResponse replies) {
        this.replies = replies;
    }
}
