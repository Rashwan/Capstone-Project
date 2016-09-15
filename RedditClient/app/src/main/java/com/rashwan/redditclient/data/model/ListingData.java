package com.rashwan.redditclient.data.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.util.List;

/**
 * Created by rashwan on 9/8/16.
 */
@AutoValue
public abstract class ListingData {
    @Nullable public abstract String after();
    @Nullable public abstract String before();
    public abstract List<ListingKind> children();

    public static TypeAdapter<ListingData> typeAdapter(Gson gson) {
        return new AutoValue_ListingData.GsonTypeAdapter(gson);
    }
}
