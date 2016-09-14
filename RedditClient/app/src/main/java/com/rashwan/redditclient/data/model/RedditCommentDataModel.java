package com.rashwan.redditclient.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rashwan on 9/14/16.
 */

public class RedditCommentDataModel extends ListingKind{
    @SerializedName("parent_id") String parentId;
    String body;

    public String getParentId() {
        return parentId;
    }

    public String getBody() {
        return body;
    }
}
