package com.rashwan.redditclient.data.model;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.util.Date;
import java.util.TimeZone;

import timber.log.Timber;

/**
 * Created by rashwan on 9/11/16.
 */

@AutoValue
public abstract class UserDetailsModel {
    public abstract String name();
    @SerializedName("created_utc") public abstract long createdUtc();
    @SerializedName("link_karma") public abstract long linkKarma();
    @SerializedName("comment_karma") public abstract long commentKarma();

    public static TypeAdapter<UserDetailsModel> typeAdapter(Gson gson) {
        return new AutoValue_UserDetailsModel.GsonTypeAdapter(gson);
    }

    public String convertUtcToLocalTime(Long utc){
        DateFormat date = DateFormat.getDateInstance();
        date.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formatted = date.format(new Date(utc*1000L));
        Timber.d(formatted);
        return formatted;
    }
}
