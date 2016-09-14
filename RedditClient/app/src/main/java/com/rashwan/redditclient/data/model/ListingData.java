package com.rashwan.redditclient.data.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by rashwan on 9/8/16.
 */

public class ListingData {
    private String after;
    private String before;
    @SerializedName("children") private List<ListingKind> children;

    public String getAfter() {
        return after;
    }

    public String getBefore() {
        return before;
    }

    public List<ListingKind> getChildren() {
        return children;
    }
}
