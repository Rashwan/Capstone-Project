package com.rashwan.redditclient.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * Created by rashwan on 9/8/16.
 */

@AutoValue
public abstract class ListingResponse {
    public abstract String kind();
    public abstract ListingData data();

    public static TypeAdapter<ListingResponse> typeAdapter(Gson gson) {
        return new AutoValue_ListingResponse.GsonTypeAdapter(gson);
    }

}
