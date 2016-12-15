package com.rashwan.redditclient.data.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rashwan on 9/10/16.
 */
@AutoValue
public abstract class SubredditDetailsModel extends ListingKind implements Parcelable{

    @SerializedName("title") public abstract String description();
    @SerializedName("display_name") public abstract String name();
    @Nullable @SerializedName("banner_img") public abstract String bannerImage();
    @SerializedName("icon_img") public abstract String subredditIcon();
    @SerializedName("subscribers") public abstract long numOfSubscribers();

    public static TypeAdapter<SubredditDetailsModel> typeAdapter(Gson gson) {
        return new AutoValue_SubredditDetailsModel.GsonTypeAdapter(gson);
    }
    @Override
    public String getType() {
        return SubredditDetailsModel.class.getSimpleName();
    }
}
