package com.rashwan.redditclient.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by rashwan on 9/7/16.
 */

@AutoValue
public abstract class AccessTokenModel {
    @Json(name = "access_token") public abstract String accessToken();

    public static JsonAdapter<AccessTokenModel> jsonAdapter(Moshi moshi){
        return AutoValue_AccessTokenModel.jsonAdapter(moshi);
    }
}
