package com.rashwan.redditclient.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by rashwan on 9/10/16.
 */

public class SubredditDetailsModel extends ListingKind{
    @SerializedName("title") public  String description;
    @SerializedName("display_name") public  String name;
    @SerializedName("banner_img") public  String bannerImage;
    @SerializedName("icon_img") public  String subredditIcon;
    @SerializedName("subscribers") public  long numOfSubscribers;

    public String getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public String getSubredditIcon() {
        return subredditIcon;
    }

    public long getNumOfSubscribers() {
        return numOfSubscribers;
    }
}
