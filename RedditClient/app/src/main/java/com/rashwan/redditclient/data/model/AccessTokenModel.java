package com.rashwan.redditclient.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

/**
 * Created by rashwan on 9/7/16.
 */

@AutoValue
public abstract class AccessTokenModel {
    @SerializedName("access_token") public abstract String accessToken();

    public static TypeAdapter<AccessTokenModel> typeAdapter(Gson gson) {
        return new AutoValue_AccessTokenModel.GsonTypeAdapter(gson);
    }
}
