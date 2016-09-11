package com.rashwan.redditclient.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by rashwan on 9/10/16.
 */

@AutoValue
public abstract class SubredditDetailsModel {
    @Json(name = "title") public abstract String description();
    @Json(name = "display_name") public abstract String name();
    @Json(name = "banner_img") public abstract String bannerImage();
    @Json(name = "icon_img") public abstract String subredditIcon();
    @Json(name = "subscribers") public abstract long numOfSubscribers();

    public static JsonAdapter<SubredditDetailsModel> jsonAdapter(Moshi moshi){
        return AutoValue_SubredditDetailsModel.jsonAdapter(moshi);
    }
}
