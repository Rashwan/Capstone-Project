package com.rashwan.redditclient.data.model;

import com.google.auto.value.AutoValue;
import com.squareup.moshi.Json;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

/**
 * Created by rashwan on 9/7/16.
 */

@AutoValue public abstract class AccessToken {
    @Json(name = "access_token") public abstract String accessToken();

    public static JsonAdapter<AccessToken> jsonAdapter(Moshi moshi){
        return AutoValue_AccessToken.jsonAdapter(moshi);
    }
}
